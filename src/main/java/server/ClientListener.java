package server;

import msg.ConnectionACKMessage;
import msg.Message;
import msg.UserListACKMessage;
import msg.UserListMessage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ClientListener extends Thread {
    private Socket socket;
    private ObjectOutputStream fout;
    private ObjectInputStream fin;
    private DataMonitor id_lista;
    private UserTable id_user;

    public ClientListener(Socket socket, Server server) {
        try {
            this.socket = socket;
            fout = new ObjectOutputStream(socket.getOutputStream());
            fin = new ObjectInputStream(socket.getInputStream());
            this.id_lista = server.getId_lista();
            this.id_user = server.getId_user();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run () {
        while(true) {
            try {
                Message msg = (Message) fin.readObject();

                switch (msg.getType()) {
                    case CONNECTION:
                        // Guarda informacion del usuario en la tabla
                        User u = new User(socket.getLocalAddress().getHostAddress(), msg.getSrc(), fout, fin);
                        id_user.add(u);
                        // Envio mensaje-confirmacion-conexion por fout
                        fout.writeObject(new ConnectionACKMessage(msg.getSrc()));
                        fout.flush();
                        break;
                    case USERLIST:
                        // Crea un mensaje con la informacion global
                        HashMap<String, Set<String>> inf_global = id_lista.getAll();
                        // Envio mensaje confirmacion lista usuarios
                        fout.writeObject(new UserListACKMessage(msg.getSrc(), inf_global));
                        fout.flush();
                        break;
                    case CLOSE:
                        // Eliminar informacion de las tablas
                        // Envio mensaje confirmacion cerrar conexion
                        break;
                    case FILEREQUEST:
                        // Buscar el usuario que tiene el archivo en las tablas
                        // y su flujo fout2
                        // Enviar mensaje emitir fichero por fout2
                        break;
                    case CLIENTSERVERREADY:
                        // Buscar el fout1 del cliente 1 que pidio el fichero
                        // Envio de mensaje preparado de servidor-cliente.
                    default:
                        System.err.println("ERROR: MENSAJE NO RECONOCIDO.");
                        break;
                }
            } catch (IOException e) {
                // e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
            }
        }
    }
}