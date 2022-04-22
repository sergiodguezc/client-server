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

import msg.ConnectionMessage;
import msg.UserListMessage;

public class Client extends JFrame implements ActionListener {
    private Socket socket;
    private ObjectInputStream fin;
    private ObjectOutputStream fout;

    // Menu atributes
    private ClientInitPanel panelInicio;
    private ClientMenuPanel panelMenu;
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
        if (e.getSource() == panelInicio.getButton()) {
            try {
                ip = panelInicio.getTextIp().getText();
                username = panelInicio.getTextUsername().getText();
                
                // Si el server no está disponible no furula
                socket = new Socket(ip, 9999);
                fout = new ObjectOutputStream(socket.getOutputStream());
                fin = new ObjectInputStream(socket.getInputStream());
                new ServerListener(socket, fin, fout).start();
                fout.writeObject(new ConnectionMessage(username, "server"));
                fout.flush();

                // Cambio de menu
                remove(panelInicio);
                add(panelMenu);
                revalidate();
                repaint();

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else if (e.getSource() == panelMenu.getButtonUserList()) {
            try {
                fout.writeObject(new UserListMessage(username));
                fout.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == panelMenu.getButtonExit()) {
            System.exit(0);
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


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
