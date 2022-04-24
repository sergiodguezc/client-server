package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import msg.*;
import server.User;

import javax.swing.*;

public class ServerListener extends Thread {
    private FileMonitor name_file;
    private Socket socket;
    private ObjectOutputStream fout;
    private ObjectInputStream fin;

    // Instancia de la aplicacion cliente para mostrar los mensajes por la GUI
    private Client client;

    public ServerListener(Socket socket, ObjectInputStream fin, ObjectOutputStream fout, FileMonitor name_file, Client client) {
        this.socket = socket;
        this.fin = fin;
        this.fout = fout;
        this.name_file = name_file;
        this.client = client;
    }

    public void run() {
        while (true) {
            try {
                Message msg = (Message) fin.readObject();
                switch (msg.getType()) {
                    case CONNECTIONACK:
                        // Mostramos el mensaje de confirmacion de conexion por la GUI
                        showACK(msg.toString());
                        break;
                    case USERLISTACK:
                        // Mostramos la informacion devuelta por el server por la GUI
                        client.showUserList(msg.getUsers());
                        break;
                    case CLOSEACK:
                        // Mostramos el mensaje de confirmacion del server por la GUI
                        showACK(msg.toString());
                        // Cerramos la conexion
                        sleep(1000);
                        fout.close();
                        fin.close();
                        socket.close();
                        return;
                    case SERVERCLIENTREADY:
                        (new Receiver(msg.getDest(), fout, fin, name_file, msg.getPort())).start();
                        break;
                    case SENDFILE:
                        (new Sender(fout, fin, msg.getDest(), msg.getReceiver(), name_file.get(msg.getFile()))).start();
                        break;
                    default:
                        System.err.println("ERROR: MENSAJE NO RECONOCIDO.");
                        break;
                }
            } catch (IOException e) {
                // e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
            } catch (InterruptedException e) {
                // throw new RuntimeException(e);
            }
        }
    }


    private void showACK(String msg) {
        JOptionPane pane = new JOptionPane(msg,
                JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Message from server");
        dialog.setModal(false);
        dialog.setVisible(true);

        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        }).start();
    }
}
