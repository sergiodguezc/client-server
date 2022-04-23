package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class ClientInitPanel extends JPanel {
    private JTextField textIp = new JTextField(20);
    private JTextField textUsername = new JTextField(20);
    private JLabel labelUsername = new JLabel("Enter username: ");
    private JLabel labelIp = new JLabel("Enter ip: ");
    private JButton okButton = new JButton("OK");

    private JButton addFilesButton = new JButton("Add Files");

	private GridBagConstraints constraints;
    private Client cliente;

    public ClientInitPanel(Client cliente) {
        super(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panelInicio
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(labelUsername, constraints);

        constraints.gridx = 1;
        this.add(textUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(labelIp, constraints);

        constraints.gridx = 1;
        this.add(textIp, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(addFilesButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        okButton.addActionListener(cliente);
        this.add(okButton, constraints);


        // set border for the panel
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Client app"));
    }

    public JTextField getTextIp() {
		return textIp;
	}

	public JTextField getTextUsername() {
		return textUsername;
	}

	public JButton getOkButton() {
        return okButton;
    }
    public JButton getAddFilesButton() {
        return addFilesButton;
    }
}
