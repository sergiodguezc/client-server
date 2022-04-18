package server;

import msg.ConnectionACKMessage;
import msg.Message;
import msg.UserListACKMessage;
import msg.UserListMessage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientListener extends Thread {
    private Socket socket;
    private ObjectOutputStream fout;
    private ObjectInputStream fin;

    public ClientListener(Socket socket) {
        try {
            this.socket = socket;
            fout = new ObjectOutputStream(socket.getOutputStream());
            fin = new ObjectInputStream(socket.getInputStream());
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
                        // Envio mensaje-confirmacion-conexion por fout
                        fout.writeObject(new ConnectionACKMessage(msg.getSrc()));
                        fout.flush();
                        break;
                    case USERLIST:
                        // Crea un mensaje con la informacion global
                        // Envio mensaje confirmacion lista usuarios
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
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}