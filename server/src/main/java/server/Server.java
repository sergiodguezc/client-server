package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() {
    }

    public static void main(String[] args) {
        try {
            // Create server socket and
            // listen for connection on port 9999
            ServerSocket listen = new ServerSocket(9999);

            while(true) {
                System.out.println("waiting for connection");
                Socket socket = listen.accept();

                // Create process for the communication with the new client
                (new OC(socket)).start();
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }
}
