package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import msg.CloseMessage;
import msg.ConnectionMessage;
import msg.FileRequestMessage;
import msg.UserListMessage;

public class Client extends JFrame implements ActionListener {
    private Socket socket;
    private ObjectInputStream fin;
    private ObjectOutputStream fout;
    private ServerListener OS;

    // Menu atributes
    private ClientInitPanel panelInicio;
    private ClientMenuPanel panelMenu;
    private ClientDownloadPanel panelDownload;
    //private ClientAddFilesPanel panelFiles;
    private JFileChooser fileChooser;
    private String ip;
    private String username;

    // Tabla para obtener el fichero solicitado por otro cliente
    // Clave : Nombre que el servidor tiene asociado a este fichero
    // Valor : File que tiene descargado el cliente con ese nombre
    private HashMap<String,File> name_files;


    public Client() {
        super("Client app");
        name_files = new HashMap<>();
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
                OS = new ServerListener(socket, fin, fout, name_files);
                OS.start();
                fout.writeObject(new ConnectionMessage(username, "server", name_files.keySet()));
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
                int retval = fileChooser.showOpenDialog(this);
                if (retval == JFileChooser.APPROVE_OPTION) {

                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void iniciarPanel() {
        setBounds(20, 20, 400, 300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel inicial
        panelInicio = new ClientInitPanel(this);
        add(panelInicio);

        // panel menu
        panelMenu = new ClientMenuPanel(this);

        // panel download
        panelDownload = new ClientDownloadPanel(this);

        // panel files
        //panelFiles = new ClientAddFilesPanel(this);
        fileChooser= new JFileChooser();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
