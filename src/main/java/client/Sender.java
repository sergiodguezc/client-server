package client;

import msg.ClientServerReadyMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Sender hace la funcion de cliente en la conexion p2p
public class Sender extends Thread {
    // Nuevo socket para la comunicacion p2p
    private ServerSocket ss;
    private Socket socket;

    // Canales de comunicacion con el server
    private ObjectOutputStream fout_server;
    private ObjectInputStream fin_server;

    // Canales de comunicacion p2p
    private ObjectOutputStream fout_p2p;
    private ObjectInputStream fin_p2p;

    // Nombre del receptor
    private String receiver;

    // Nombre del emisor
    private String sender;

    public Sender(ObjectOutputStream fout_server, ObjectInputStream fin_server, String sender, String receiver) {
        this.fout_server = fout_server;
        this.fin_server = fin_server;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void run() {
        try {
            // Enviamos el mensaje al servidor de que esta preparado
            fout_server.writeObject(new ClientServerReadyMessage(sender, receiver));
            fout_server.flush();

            // Abrimos un nuevo puerto para empezar la comunicacion con el receptor
            ss = new ServerSocket(0);
            socket = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public
}