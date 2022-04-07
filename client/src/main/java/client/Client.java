package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main (String args[]) {
        try {
            Socket socket = new Socket(args[0], 9999);
            ObjectOutputStream to_server = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream from_server = new ObjectInputStream(socket.getInputStream());

            String msg = (String)from_server.readObject();
            to_server.writeObject("adios"); to_server.flush();

            System.out.println("Cliente: Recibo hola del servidor");
            System.out.println("Mensaje: " + msg);
            System.out.println("Cliente: Escribo adios al servidor");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
