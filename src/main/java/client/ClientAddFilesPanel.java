package client;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ClientAddFilesPanel extends JDialog {
    private JFileChooser fileChooser = new JFileChooser();

    private GridBagConstraints constraints;
    private Client cliente;

    public ClientAddFilesPanel(Client cliente) {
        this.add(fileChooser);
    }

    public File[] getSelectedFiles() {
        return fileChooser.getSelectedFiles();
    }

    public int openDialog() {
        return fileChooser.showOpenDialog(cliente);
    }
}

