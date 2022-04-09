package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OC extends Thread {
    private Socket socket;
    public OC(Socket socket) {
        this.socket = socket;
    }

    public void run () {
        // Create input and output streams to talk to client
        try {
            ObjectOutputStream fout = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fin = new ObjectInputStream(socket.getInputStream());

            // Get filename from client and check if it exists
            String filename = (String) fin.readObject();
            File inputFile = new File(filename);
            if(!inputFile.exists()) {
                fout.writeObject("cannot open " + filename);
                fout.close(); fin.close();
                socket.close();
            }
            else {
                System.out.println("reading from file " + filename);
                BufferedReader input = new BufferedReader(new FileReader(inputFile));
                String line;
                while((line = input.readLine()) != null) {
                    fout.writeObject(line); fout.flush();
                }
                fout.close(); fin.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
