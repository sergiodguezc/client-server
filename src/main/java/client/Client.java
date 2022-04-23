package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
    protected String ip;
    protected String username;


    public Client() {
        super("Client app");
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
            if (e.getSource() == panelInicio.getButton()) {
                ip = panelInicio.getTextIp().getText();
                username = panelInicio.getTextUsername().getText();
                
                // Si el server no está disponible no furula
                socket = new Socket(ip, 9999);
                fout = new ObjectOutputStream(socket.getOutputStream());
                fin = new ObjectInputStream(socket.getInputStream());

                // Creamos un proceso que reciba los mensajes del servidor
                OS = new ServerListener(socket, fin, fout);
                OS.start();
                fout.writeObject(new ConnectionMessage(username, "server"));
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

        panelDownload = new ClientDownloadPanel(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
