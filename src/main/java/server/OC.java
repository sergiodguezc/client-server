package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

                // Read file into a list of messages. Deliver this list to client
                String line; ArrayList<String> msg = new ArrayList<String>();
                while((line = input.readLine()) != null) {
                    msg.add(line);
                }
                fout.writeObject(msg);
                fout.flush();

                // Close the piper
                fout.close();
                fin.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}