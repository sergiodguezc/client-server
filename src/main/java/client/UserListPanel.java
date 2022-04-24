package client;

import server.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserListPanel extends JPanel {
   JTable table;
   JScrollPane scrollPane;
   JButton menuButton = new JButton("Menu");

   public UserListPanel(Client cliente, ArrayList<User> users) {
       super(new GridBagLayout());

       table = new JTable(createTableModel(users));
       scrollPane = new JScrollPane(table);
       table.setFillsViewportHeight(true);

       this.add(scrollPane);

       menuButton.addActionListener(cliente);
       this.add(menuButton);
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

    public JButton getMenuButton() {
       return menuButton;
    }
}
