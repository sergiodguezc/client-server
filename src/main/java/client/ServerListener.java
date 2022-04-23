package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import msg.*;

public class ServerListener extends Thread {
    private HashMap<String, File> name_file;
    private Socket socket;
    private ObjectOutputStream fout;
    private ObjectInputStream fin;

    public ServerListener(Socket socket, ObjectInputStream fin, ObjectOutputStream fout, HashMap<String, File> name_file) {
        this.socket = socket;
        this.fin = fin;
        this.fout = fout;
        this.name_file = name_file;
    }

    public void run() {
        while (true) {
            try {
                Object obj = fin.readObject();
                Message msg = (Message) obj;
                switch (msg.getType()) {
                    case CONNECTIONACK:
                    case USERLISTACK:
                        // Mostramos la informacion devuelta por el server
                        System.out.println(msg);
                        break;
                    case CLOSEACK:
                        System.out.println(msg);
                        fout.close();
                        fin.close();
                        socket.close();
                        return;
                    case SERVERCLIENTREADY:
                        (new Receiver(msg.getDest(), fout, fin, name_file)).start();
                        break;
                    case SENDFILE:
                        (new Sender(fout, fin, msg.getDest(), msg.getReceiver(), msg.getFile())).start();
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
