package client;

import msg.ClientServerReadyMessage;

import java.io.*;
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
    private PrintWriter fout_p2p;
    private BufferedReader fin_p2p;

    // Nombre del receptor
    private String receiver;

    // Nombre del emisor
    private String sender;

    // Fichero que envía
    private File fichero;

    public Sender(ObjectOutputStream fout_server, ObjectInputStream fin_server, String sender, String receiver, File file) {
        this.fout_server = fout_server;
        this.fin_server = fin_server;
        this.sender = sender;
        this.receiver = receiver;
        this.fichero = file;
    }

    public void run() {
        try {
            // Abrimos un nuevo puerto para empezar la comunicacion con el receptor
            ss = new ServerSocket(0);

            // Enviamos el mensaje al servidor de que esta preparado
            fout_server.writeObject(new ClientServerReadyMessage(sender, receiver, ss.getLocalPort()));
            fout_server.flush();

            // Esperamos a que se conecte el receptor y creamos los canales
            socket = ss.accept();
            fout_p2p = new PrintWriter(socket.getOutputStream());
            fin_p2p = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Cuando el receptor está conectado le pasamos la información
            BufferedReader input = new BufferedReader(fichero)

            // Terminamos la conexion p2p
            fout_p2p.close();
            fin_p2p.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
