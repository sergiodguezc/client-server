package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
			ServerSocket listen = new ServerSocket(9999);

            while(true) {
                System.out.println("Waiting for connection...");
                Socket socket = listen.accept();

                BufferedReader from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter to_client = new PrintWriter(socket.getOutputStream());

                // Get filename from client and checks if it exists
                String filename = from_client.readLine();
                File inputFile = new File(filename);
                if (!inputFile.exists()) {
                    to_client.println("cannot open " + filename);
                    to_client.close(); from_client.close();
                    socket.close();
                    continue;
                }

                // read lines from filename and send to client
                System.out.println("reading from file "+ filename);
                BufferedReader input = new BufferedReader(new FileReader(inputFile));
                String line;

                while ((line = input.readLine()) != null)
                    to_client.println(line);
                to_client.close(); from_client.close();
                socket.close();
            }
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
