package client;

import server.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserListPanel extends JPanel {
   JTable table = new JTable();
   JButton menuButton = new JButton("Menu");

    private GridBagConstraints constraints;

   public UserListPanel(Client cliente, ArrayList<User> users) {
       super(new GridBagLayout());

       constraints = new GridBagConstraints();
       constraints.anchor = GridBagConstraints.WEST;
       constraints.insets = new Insets(10, 10, 10, 10);

       initTable(users);

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
       for (String d : data) {
           for (int i=0;i<3;i++) {
               d[i][0] = users.get(i).getUsername();
               d[i][1] = users.get(i).getIp();
               d[i][2] = users.get(i).getDescargas();
           }
       }
       return new DefaultTableModel(data,columnNames);

    }
}
