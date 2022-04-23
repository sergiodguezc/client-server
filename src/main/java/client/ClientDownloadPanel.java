package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientDownloadPanel extends JPanel {
    private JTextField textfilename = new JTextField(20);
    private JLabel labelfile = new JLabel("Enter file name: ");
    private JButton button = new JButton("OK");

    private GridBagConstraints constraints;
    private Client cliente;

    public ClientDownloadPanel(Client cliente) {
        super(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panelInicio
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(labelfile, constraints);

        constraints.gridx = 1;
        this.add(textfilename, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        button.addActionListener(cliente);
        this.add(button, constraints);

        // set border for the panel
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Client app"));
    }

    public JTextField getTextfilename() {
        return textfilename;
    }

    public JButton getButton() {
        return button;
    }
}
