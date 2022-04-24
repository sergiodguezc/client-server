package client;

import javax.swing.*;
import java.awt.*;

public class CloseACKPanel extends JPanel {

    private JButton okButton = new JButton("OK");
    private JLabel closingMsgLabel;
    private Client cliente;

    private GridBagConstraints constraints;

    public CloseACKPanel(Client cliente, String msgACK) {
        super(new GridBagLayout());
        this.cliente = cliente;
        closingMsgLabel = new JLabel(msgACK);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(closingMsgLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        okButton.addActionListener(cliente);
        this.add(okButton, constraints);

        // set border for the panel
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),"Message ACK from server"));
    }

    public JButton getOkButton() {
        return okButton;
    }

}
