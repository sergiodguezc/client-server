package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;

import lock.LockBakery;
import msg.*;

import javax.swing.*;

public class ServerListener extends Thread {
    private FileMonitor name_file;
    private Socket socket;
    private ObjectOutputStream fout;
    private ObjectInputStream fin;

    // Instancia de la aplicacion cliente para mostrar los mensajes por la GUI
    private Client client;

    // Lock para que los comandos sean bloqueantes
    private LockBakery lock_GUI;

    // Lock para que la escritura
    private Lock lock_fout;

    public ServerListener(Socket socket, ObjectInputStream fin, ObjectOutputStream fout, FileMonitor name_file, Client client, LockBakery lock_GUI, Lock lock_fout) {
        this.socket = socket;
        this.fin = fin;
        this.fout = fout;
        this.name_file = name_file;
        this.client = client;
        this.lock_GUI = lock_GUI;
        this.lock_fout = lock_fout;
    }

    public void run() {
        while (true) {
            try {
                Message msg = (Message) fin.readObject();
                // Bloqueamos la interfaz
                lock_GUI.takeLock(2);
                switch (msg.getType()) {
                    case CONNECTIONACK:
                        // Mostramos el mensaje de confirmacion de conexion por la GUI
                        showConnectionACK(msg.toString());
                        break;
                    case USERLISTACK:
                        // Mostramos la informacion global del server por la GUI
                        client.showUserList(msg.getUsers());
                        break;
                    case CLOSEACK:
                        // Mostramos el mensaje de confirmacion de conexion por la GUI
                        client.showCloseACK(msg.toString());
                        return;
                    case SERVERCLIENTREADY:
                        (new Receiver(msg.getDest(), fout, fin, name_file, msg.getPort(), lock_fout)).start();
                        break;
                    case SENDFILE:
                        (new Sender(fout, fin, msg.getDest(), msg.getReceiver(), name_file.get(msg.getFile()), lock_fout)).start();
                        break;
                    default:
                        System.err.println("ERROR: MENSAJE NO RECONOCIDO.");
                        break;
                }
                lock_GUI.releaseLock(2);
            } catch (IOException e) {
                // e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
            }
        }
    }


    private void showConnectionACK(String msg) {
        JOptionPane pane = new JOptionPane(msg,
                JOptionPane.PLAIN_MESSAGE);
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
