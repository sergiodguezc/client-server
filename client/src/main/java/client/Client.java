package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        try {
            // Leer nombre de usuario. Lo leemos de args[0]
            if (args.length != 2) {
                System.out.println("need 2 arguments.");
                System.exit(1);;
            }
            String host = args[0];
            String filename = args[1];

            // Abrimos un socket, la entrada y salida leen y escriben sobre él.
            Socket socket = new Socket(host, 9999);
            BufferedReader from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter to_server = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            to_server.println(filename); to_server.flush();

            String line;
            while ((line = from_server.readLine()) != null) {
                System.out.println(line);
            }
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
