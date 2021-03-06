package client;

import msg.ClientServerReadyMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;

// Sender hace la funcion de cliente en la conexion p2p
public class Sender extends Thread {
    // Nuevo socket para la comunicacion p2p
    private ServerSocket ss;
    private Socket socket;

    // Canales de comunicacion con el server
    private ObjectOutputStream fout_server;
    private ObjectInputStream fin_server;

    // Canales de comunicacion p2p
    private PrintWriter fout_p2p;
    private BufferedReader fin_p2p;

    // Nombre del receptor
    private String receiver;

    // Nombre del emisor
    private String sender;

    // Fichero que envía
    private File fichero;

    // Lock para que la escritura del fout_server sea atomica
    private Lock lock_fout;

    public Sender(ObjectOutputStream fout_server, ObjectInputStream fin_server, String sender, String receiver, File file, Lock lock_fout) {
        this.fout_server = fout_server;
        this.fin_server = fin_server;
        this.sender = sender;
        this.receiver = receiver;
        this.fichero = file;
        this.lock_fout = lock_fout;
    }

    public void run() {
        try {
            // Abrimos un nuevo puerto para empezar la comunicacion con el receptor
            ss = new ServerSocket(0);

            // Enviamos el mensaje al servidor de que esta preparado
            lock_fout.lock();
            fout_server.writeObject(new ClientServerReadyMessage(sender, receiver, ss.getLocalPort()));
            fout_server.flush();
            lock_fout.unlock();

            // Esperamos a que se conecte el receptor y creamos los canales
            socket = ss.accept();
            fout_p2p = new PrintWriter(socket.getOutputStream());
            fin_p2p = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Cuando el receptor está conectado le pasamos primero el nombre del fichero
            fout_p2p.println(fichero.getName());
            fout_p2p.flush();

            // y luego el fichero linea por linea
            BufferedReader input = new BufferedReader(new FileReader(fichero));
            String line;
            while((line =  input.readLine()) != null)
                fout_p2p.println(line);
            input.close();

            // Terminamos la conexion p2p
            fout_p2p.close();
            fin_p2p.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
