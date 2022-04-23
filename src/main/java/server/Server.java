package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
// TODO: Ver qué pasa con esto
import lock.LockBakery;

import javax.swing.JFrame;

public class Server extends JFrame {
    // Necesitamos dos tablas que almacenan la informacion del
    // sistema que se accederan de manera concurrente mediante
    // el problema de lector-escritor.

    // Tabla con la informacion disponible en el sistema. Lo
    // implementamos en la clase DataMonitor, donde
    // explicamos detalladamente la estructura de datos
    // que utilizamos. El problema de lector escritor lo
    // resolvemos aqui con un Monitor con Locks y Variables
    // Condicion.
    private DataMonitor id_lista;

    // Tabla con la informacion de los usuarios. Lo implementamos
    // en la clase UserTable. El problema de lector-escritor
    // lo resolvemos aqui con semaforos.
    private UserTable id_user;

    // La razon por la que utilizamos dos tablas es porque
    // en el problema de lector-escritor, queremos aprovechar
    // el hecho de que los lectores pueden acceder concurrentemente.
    // Por ello si hay solo un escritor en una tabla, en la otra
    // se puede seguir accediendo.

    public Server() {
        id_user = new UserTable();
        id_lista = new DataMonitor();
    }

    public static void main(String[] args) {
        try {
            // Create server socket and
            // listen for connection on port 9999
            ServerSocket listen = new ServerSocket(9999);
            Server server = new Server();

            while(true) {
                Socket socket = listen.accept();

                // Create process for the communication with the new client
                (new ClientListener(socket, server)).start();
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    public DataMonitor getId_lista() {
        return id_lista;
    }

    public UserTable getId_user() {
        return id_user;
    }
}
