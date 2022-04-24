package client;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import msg.CloseMessage;
import msg.ConnectionMessage;
import msg.FileRequestMessage;
import msg.UserListMessage;
import server.User;

public class Client extends JFrame implements ActionListener {
    private Socket socket;
    private ObjectInputStream fin;
    private ObjectOutputStream fout;
    private ServerListener OS;

    // Menu atributes
    private ClientInitPanel panelInicio;
    private ClientMenuPanel panelMenu;
    private ClientDownloadPanel panelDownload;
    private UserListPanel userListPanel;
    private String ip;
    private String username;

    // Tabla para obtener el fichero solicitado por otro cliente
    // Clave : Nombre que el servidor tiene asociado a este fichero
    // Valor : File que tiene descargado el cliente con ese nombre
    private FileMonitor name_files;


    public Client() {
        super("Client app");
        name_files = new FileMonitor();
        iniciarPanel();
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Client();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == panelInicio.getOkButton()) {
                ip = panelInicio.getTextIp().getText();
                username = panelInicio.getTextUsername().getText();
                
                // Si el server no está disponible no furula
                socket = new Socket(ip, 9999);
                fout = new ObjectOutputStream(socket.getOutputStream());
                fin = new ObjectInputStream(socket.getInputStream());

                // Creamos un proceso que reciba los mensajes del servidor
                OS = new ServerListener(socket, fin, fout, name_files, this);
                OS.start();

                // Convertimos los (String -> File) -> String[] para
                // notificar al servidor que archivos tiene el usuario nuevo
                // Notese que no es necesario concurrencia ya que
                ArrayList<String> filenames = new ArrayList<>();
                for (String name : name_files.keyList())
                    filenames.add(name);
                fout.writeObject(new ConnectionMessage(username, "server", filenames));
                fout.flush();

                // Cambio de menu
                remove(panelInicio);
                add(panelMenu);
                revalidate();
                repaint();

            } else if (e.getSource() == panelMenu.getButtonUserList()) {
                fout.writeObject(new UserListMessage(username));
                fout.flush();

            } else if (e.getSource() == panelMenu.getButtonExit()) {
                fout.writeObject(new CloseMessage(username));
                fout.flush();
                OS.join();
                System.exit(0);

            } else if (e.getSource() == panelMenu.getButtonDownload()) {
                // Cambio de panel
                remove(panelMenu);
                add(panelDownload);
                revalidate();
                repaint();
            } else if(e.getSource() == panelDownload.getButton()) {
                String file = panelDownload.getTextfilename().getText();
                fout.writeObject(new FileRequestMessage(username, file));
                fout.flush();

                remove(panelDownload);
                add(panelMenu);
                revalidate();
                repaint();
            } else if (e.getSource() == panelInicio.getAddFilesButton()) {
                int retval = panelInicio.openDialog();
                if (retval == JFileChooser.APPROVE_OPTION) {
                    File[] files = panelInicio.getSelectedFiles();
                    for (File f : files) {
                        name_files.put(f.getName(),f);
                        panelInicio.addTextArea(f);
                    }
                }
            } else if (e.getSource() == userListPanel.getMenuButton()) {
                remove(userListPanel);
                add(panelMenu);
                revalidate();
                repaint();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void iniciarPanel() {
        setBounds(20, 20, 400, 500);
        setResizable(true);
        setCloseWindow();

        // Panel inicial
        panelInicio = new ClientInitPanel(this);
        add(panelInicio);

        // panel menu
        panelMenu = new ClientMenuPanel(this);

        // panel download
        panelDownload = new ClientDownloadPanel(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setCloseWindow() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    if(fout != null) {
                        fout.writeObject(new CloseMessage(username));
                        fout.flush();
                        OS.join();
                    }
                    System.exit(0);
                } catch (IOException ex) {
                    // throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    //throw new RuntimeException(ex);
                }
            }
        });
    }

    public void showUserList(ArrayList<User> users) {
        remove(panelMenu);
        userListPanel = new UserListPanel(this,users);
        add(userListPanel);
        revalidate();
        repaint();
    }
}
