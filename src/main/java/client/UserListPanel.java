package client;

import server.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserListPanel extends JPanel {
   JTable table;
   JButton menuButton = new JButton("Menu");

    private GridBagConstraints constraints;

   public UserListPanel(Client cliente, ArrayList<User> users) {
       super(new GridBagLayout());

       constraints = new GridBagConstraints();
       constraints.anchor = GridBagConstraints.WEST;
       constraints.insets = new Insets(10, 10, 10, 10);

       table = new JTable(createTableModel(users));

       // add components to the panel
       constraints.gridx = 0;
       constraints.gridy = 0;
       this.add(table, constraints);

       constraints.gridx = 3;
       constraints.gridy = 3;
       constraints.gridwidth = 2;
       constraints.anchor = GridBagConstraints.CENTER;
       menuButton.addActionListener(cliente);
       this.add(menuButton, constraints);

       // set border for the panel
       this.setBorder(BorderFactory.createTitledBorder(
               BorderFactory.createEtchedBorder(), "User list"));
   }

    private DefaultTableModel createTableModel(ArrayList<User> users) {
       String[] columnNames = {"Username", "IP", "Files"};
       String[][] data = new String[users.size()][3];
       for (int i = 0; i < users.size(); i++) {
               data[i][0] = users.get(i).getUsername();
               data[i][1] = users.get(i).getIp();
               data[i][2] = users.get(i).getDescargas().toString();
       }
       return new DefaultTableModel(data,columnNames);

    }
}
