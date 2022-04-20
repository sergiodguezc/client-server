package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import lock.LockBakery;

public class Server extends JFrame {
    // Puerto asignado es un atributo que usaremos en las conexiones p2p
    // y que lo protegeremos con con un LockBakery, implementado en la práctica
    // 2.
    private LockBakery lock;

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
        lock = new LockBakery(0);
    }

    public static void main(String[] args) {
        try {
            // Create server socket and
            // listen for connection on port 9999
            ServerSocket listen = new ServerSocket(9999);

            while(true) {
                System.out.println("waiting for connection");
                Socket socket = listen.accept();

                // Create process for the communication with the new client
                (new ClientListener(socket)).start();
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }
}
