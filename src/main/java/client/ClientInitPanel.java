package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientInitPanel extends JPanel {
    private JTextField textIp = new JTextField(20);
    private JTextField textUsername = new JTextField(20);
    private JLabel labelUsername = new JLabel("Enter username: ");
    private JLabel labelIp = new JLabel("Enter ip: ");
    private JButton button = new JButton("OK");

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
        button.addActionListener(cliente);
        this.add(button, constraints);

        // set border for the panel
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Client app"));
    }

    public JTextField getTextIp() {
		return textIp;
	}

	public void setTextIp(JTextField textIp) {
		this.textIp = textIp;
	}

	public JTextField getTextUsername() {
		return textUsername;
	}

	public void setTextUsername(JTextField textUsername) {
		this.textUsername = textUsername;
	}

	public JLabel getLabelUsername() {
		return labelUsername;
	}

	public void setLabelUsername(JLabel labelUsername) {
		this.labelUsername = labelUsername;
	}

	public JLabel getLabelIp() {
		return labelIp;
	}

	public void setLabelIp(JLabel labelIp) {
		this.labelIp = labelIp;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public GridBagConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(GridBagConstraints constraints) {
		this.constraints = constraints;
	}

	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
}
