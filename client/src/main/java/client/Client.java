package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public Client() {
    }

    public static void main(String[] args) {
        try {
            // Read command line arguments
            String host = args[0];
            String filename = args[1];

            // New thread for the communication with the server
            OS os = new OS(host, filename);
            os.start();

            // Wait for the communication to close
            os.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}