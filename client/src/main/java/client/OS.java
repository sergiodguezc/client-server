package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OS extends Thread {
    private String host;
    private String filename;
    public OS(String host, String filename) {
        this.host = host;
        this.filename = filename;
    }
    public void run() {
        try {
            // Open socket, then input and output streams to it
            Socket socket = new Socket(host, 9999);
            ObjectOutputStream fout = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fin = new ObjectInputStream(socket.getInputStream());

            // Send filename to server, then read and print lines
            // Until server closes the connection
            fout.writeObject(filename); fout.flush();
            String line;
            while((line = (String) fin.readObject()) != null) {
                System.out.println(line);
            }

            // Close the communication
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
