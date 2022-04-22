package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import msg.*;

public class ServerListener extends Thread {
    private Socket socket;
    private ObjectOutputStream fout;
    private ObjectInputStream fin;

    public ServerListener(Socket socket, ObjectInputStream fin, ObjectOutputStream fout) {
        this.socket = socket;
        this.fin = fin;
        this.fout = fout;
    }

    public void run() {
        while (true) {
            try {
                Message msg = (Message) fin.readObject();
                switch (msg.getType()) {
                    case CONNECTIONACK:
                    case USERLISTACK:
                        // Mostramos la informacion devuelta por el server
                        System.out.println(msg);
                        break;
                    case SERVERCLIENTREADY:
                        // (new Receiver()).start();
                        break;
                    case SENDFILE:
                        // (new Sender()).start();
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
