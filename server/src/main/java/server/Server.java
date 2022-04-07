package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String args[]) {
        try {
            ServerSocket listen = new ServerSocket(9999);
            while(true) {
                Socket socket = listen.accept();
                ObjectOutputStream to_client = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream from_client = new ObjectInputStream(socket.getInputStream());

                to_client.writeObject("Hola"); to_client.flush();
                String msg = (String) from_client.readObject();

                System.out.println("Servidor: Escribo hola al cliente ");
                System.out.println("Servidor: Recibo adios del cliente");
                System.out.println("Mensaje: " + msg);

                socket.close();
                listen.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
