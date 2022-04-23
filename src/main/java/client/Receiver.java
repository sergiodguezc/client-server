package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Receiver hace la funcio de server en la conexion p2p
public class Receiver extends Thread {
    // Socket para la comunicacion p2p
    private Socket socket;

    // Canales de comunicacion p2p
    private ObjectOutputStream fout_p2p;
    private ObjectInputStream fin_p2p;

    public Receiver() {
        try {
            socket = new Socket("localhost",0);
            fout_p2p = new ObjectOutputStream(socket.getOutputStream());
            fin_p2p = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

    }
}
