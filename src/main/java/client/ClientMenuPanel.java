package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ClientMenuPanel extends JPanel {
    private JButton buttonUserList = new JButton("User List");
    private JButton buttonExit = new JButton("Exit");
    private JButton buttonDownload = new JButton("Download file");
	private Client cliente;

    public ClientMenuPanel(Client cliente) {
        super(new GridBagLayout());
        this.cliente = cliente;
        initPanel();
    }

    void initPanel() {
        GridBagConstraints constraintsmenu = new GridBagConstraints();
        constraintsmenu.anchor = GridBagConstraints.WEST;
        constraintsmenu.insets = new Insets(10, 10, 10, 10);

        constraintsmenu.gridx = 1;
        constraintsmenu.gridy = 1;
        constraintsmenu.gridwidth = 2;
        constraintsmenu.anchor = GridBagConstraints.CENTER;
        buttonUserList.addActionListener(cliente);
        this.add(buttonUserList, constraintsmenu);

        constraintsmenu.gridx = 1;
        constraintsmenu.gridy = 2;
        constraintsmenu.gridwidth = 2;
        constraintsmenu.anchor = GridBagConstraints.CENTER;
        buttonDownload.addActionListener(cliente);
        this.add(buttonDownload, constraintsmenu);

        constraintsmenu.gridx = 1;
        constraintsmenu.gridy = 3;
        constraintsmenu.gridwidth = 2;
        constraintsmenu.anchor = GridBagConstraints.CENTER;
        buttonExit.addActionListener(cliente);
        this.add(buttonExit, constraintsmenu);

        // set border for the panel
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Client app menu"));
    }

    public JButton getButtonUserList() {
		return buttonUserList;
	}

	public JButton getButtonExit() {
		return buttonExit;
	}

    public JButton getButtonDownload() {
        return buttonDownload;
    }
}
