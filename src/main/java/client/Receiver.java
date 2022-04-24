package client;

import msg.FileReceivedMessage;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

// Receiver hace la funcion de server en la conexion p2p
public class Receiver extends Thread {
    // Tabla del cliente con los ficheros para modificar
    // que tiene un nuevo fichero descargado
    private FileMonitor name_file;

    // Socket para la comunicacion p2p
    private Socket socket;

    // Canales de comunicacion con el servidor
    private ObjectOutputStream fout_server;
    private ObjectInputStream fin_server;

    // Canales de comunicacion p2p
    private PrintWriter fout_p2p;
    private BufferedReader fin_p2p;

    // Nombre del receptor
    private String username;

    public Receiver(String username, ObjectOutputStream fout_server, ObjectInputStream fin_server, FileMonitor name_file, int port_sender) {
        try {
            socket = new Socket("localhost",port_sender);
            fout_p2p = new PrintWriter(socket.getOutputStream());
            fin_p2p = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.fout_server = fout_server;
            this.fin_server = fin_server;
            this.name_file = name_file;
            this.username = username;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // Espera a recibir el fichero
        try {
            // Recibimos el nombre del fichero solicitado
            String name = fin_p2p.readLine();

            // Creamos un nuevo fichero a partir del fichero que le manda el sender
            File file = new File(name);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String line;
            while((line = fin_p2p.readLine()) != null) {
                bw.write(line + '\n');
            }
            bw.close();

            // Modificamos la tabla del cliente con este nuevo fichero
            name_file.put(name, file);

            // Confirmamos al servidor que la comunicacion p2p se ha realizado exitosamente
            // Para que el servidor modifique la informacion global.
            fout_server.writeObject(new FileReceivedMessage(username, name));
            fout_server.flush();

            // Terminamos la conexion p2p
            fout_p2p.close();
            fin_p2p.close();
            socket.close();

        } catch (IOException e) {
            // throw new RuntimeException(e);
        }
    }
}
