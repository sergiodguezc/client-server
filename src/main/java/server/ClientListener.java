package server;

import msg.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
                User u;

                switch (msg.getType()) {
                    case CONNECTION:
                        u = new User(socket.getLocalAddress().getHostAddress(), msg.getSrc(), fout, fin);

                        // Guardamos los nuevos ficheros que tiene este usuario en id_lista
                        for(String file : msg.getFiles()) {
                            id_lista.add(file, u.getUsername());
                            u.addData(file);
                        }

                        // Guarda informacion del usuario en la tabla id_user
                        id_user.add(u);
                        // Envio mensaje-confirmacion-conexion por fout
                        u.lock();
                        fout.writeObject(new ConnectionACKMessage(msg.getSrc()));
                        fout.flush();
                        u.unlock();
                        break;
                    case USERLIST:
                        // Crea un mensaje con la informacion global
                        HashMap<String, User> copia_id_user = id_user.getAll();
                        // Envio mensaje confirmacion lista usuarios
                        u = id_user.get(msg.getSrc());
                        u.lock();
                        fout.writeObject(new UserListACKMessage(msg.getSrc(), copia_id_user));
                        fout.flush();
                        u.unlock();
                        break;
                    case CLOSE:
                        // Eliminar informacion de las tablas
                        User to_delete = id_user.get(msg.getSrc());
                        // Modificamos las entradas de id_lista donde
                        // el usuario tiene un archivo
                        for(String file : to_delete.getDescargas())
                            id_lista.delete(file,to_delete.getUsername());
                        // Eliminamos la entrada asociada a este usuario
                        id_user.delete(msg.getSrc());

                        // Envio mensaje confirmacion cerrar conexion
                        to_delete.lock();
                        fout.writeObject(new CloseACKMessage(msg.getSrc()));
                        fout.flush();

                        // Terminamos el proceso
                        fout.close();
                        to_delete.unlock();
                        fin.close();

                        socket.close();
                        return;
                    case FILEREQUEST:
                        // Buscar el usuario que tiene el archivo en las tablas
                        // y su flujo fout2
                        String name_sender = (String) id_lista.get(msg.getFile()).toArray()[0];
                        User sender = id_user.get(name_sender);
                        ObjectOutputStream fout2 = sender.getFout();

                        // Enviar mensaje emitir fichero por fout2
                        // El receptor de la comunicacion es el msg.getSrc()


                        sender.lock();
                        fout2.writeObject(new SendFileMessage(name_sender, msg.getSrc(), msg.getFile()));
                        fout2.flush();
                        sender.unlock();
                        break;
                    case CLIENTSERVERREADY:
                        // Buscar el fout1 del cliente 1 que pidio el fichero
                        String name_receiver = msg.getReceiver();
                        User receiver = id_user.get(name_receiver);
                        ObjectOutputStream fout1 = receiver.getFout();

                        // Envio de mensaje preparado de servidor-cliente.
                        receiver.lock();
                        int port_sender = msg.getPort();
                        fout1.writeObject(new ServerClientReadyMessage(name_receiver,port_sender));
                        receiver.unlock();
                        break;
                    case FILERECEIVED:
                        // Modificar la tabla id_lista, a??adiendo al receptor como
                        // nuevo usuario que tiene ese fichero
                        id_lista.add(msg.getFile(), msg.getSrc());

                        // Modificar la tabla id_user, a??adiendo el file al usuario
                        // que tiene ese fichero
                        id_user.get(msg.getSrc()).addData(msg.getFile());
                        break;
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