package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import msg.ConnectionMessage;

public class Client extends JFrame implements ActionListener {
    private Socket socket;
    private ObjectInputStream fin;
    private ObjectOutputStream fout;
    private ServerListener sl;

    // Menu atributes
    private JPanel panelInicio;
    private JPanel panelMenu;
    private JTextField textIp = new JTextField(20);
    private JTextField textUsername = new JTextField(20);
    private JLabel labelUsername = new JLabel("Enter username: ");
    private JLabel labelIp = new JLabel("Enter ip: ");
    private JButton button = new JButton("OK");
    protected String ip;
    protected String username;

    private JButton buttonUserList = new JButton("User List");
    private JButton buttonExit = new JButton("Exit");

    public Client() {
        super("Client app");
        iniciarPanel();
    }

    private void iniciarPanel() {
        setBounds(10, 10, 400, 300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Config
        panelInicio = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panelInicio
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelInicio.add(labelUsername, constraints);

        constraints.gridx = 1;
        panelInicio.add(textUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panelInicio.add(labelIp, constraints);

        constraints.gridx = 1;
        panelInicio.add(textIp, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        button.addActionListener(this);
        panelInicio.add(button, constraints);

        // set border for the panel
        panelInicio.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Client app"));

        // add the panel to this frame
        add(panelInicio);

        // panel menu
        panelMenu = new JPanel(new GridBagLayout());
        GridBagConstraints constraintsmenu = new GridBagConstraints();
        constraintsmenu.anchor = GridBagConstraints.WEST;
        constraintsmenu.insets = new Insets(10, 10, 10, 10);

        constraintsmenu.gridx = 1;
        constraintsmenu.gridy = 1;
        constraintsmenu.gridwidth = 2;
        constraintsmenu.anchor = GridBagConstraints.CENTER;
        buttonUserList.addActionListener(this);
        panelMenu.add(buttonUserList, constraintsmenu);

        constraintsmenu.gridx = 1;
        constraintsmenu.gridy = 2;
        constraintsmenu.gridwidth = 2;
        constraintsmenu.anchor = GridBagConstraints.CENTER;
        buttonExit.addActionListener(this);
        panelMenu.add(buttonExit, constraintsmenu);
        // set border for the panel
        panelMenu.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Client app menu"));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
        if (e.getSource() == button) {
            try {
                ip = textIp.getText();
                username = textUsername.getText();
                
                // Si el server no está disponible no furula
                socket = new Socket(ip, 9999);
                fout = new ObjectOutputStream(socket.getOutputStream());
                fin = new ObjectInputStream(socket.getInputStream());
                new ServerListener(socket, fin, fout).start();
                fout.writeObject(new ConnectionMessage(ip, username));
                fout.flush();

                // Cambio de menu
                remove(panelInicio);
                add(panelMenu);
                revalidate();
                repaint();

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else if (e.getSource() == buttonUserList) {

        } else if (e.getSource() == buttonExit) {
            System.exit(0);
        }
    }
}
