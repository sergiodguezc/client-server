package client;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// MONITOR que resuelve el problema de lectores-escritores
// con la tabla del cliente que almacena los archivos
// que tiene ese cliente descargado.
// La necesidad de esta estructura de datos es para
// relacionar los archivos de todos los clientes con los
// nombres que tiene asignado el servidor.

public class FileMonitor {

    // Utilizamos como estructura de datos una tabla Hash
    // con clave = identificador del archivo
    // y valor = archivo

    private HashMap<String, File> id_file;
    private Lock lock;
    private volatile int nr, nw;
    private Condition oktoread, oktowrite;

    public FileMonitor() {
        id_file = new HashMap<>();
        lock = new ReentrantLock(true);
        oktoread = lock.newCondition();
        oktowrite = lock.newCondition();
    }

    // Lectura de un elemento de la tabla
    public File get(String id) {
        requestRead();
        File file = id_file.get(id);
        releaseRead();
        return file;
    }

    public void put(String filename, File file) {
        requestWrite();
        id_file.put(filename, file);
        releaseWrite();
    }

    // Métodos privados que encapsulan la lectura y escritura
    private void requestRead() {
        lock.lock();
        while (nw > 0) {
            try {
                oktoread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    private void releaseRead() {
        lock.lock();
        nr = nr - 1;
        if (nr == 0)
            oktowrite.signal(); // Despierta un escritor
        lock.unlock();
    }

    private void requestWrite() {
        lock.lock();
        while (nr > 0 || nw > 0)
            try {
                oktowrite.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        nw = nw + 1;
        lock.unlock();
    }

    private void releaseWrite() {
        lock.lock();
        nw = nw - 1;
        oktowrite.signal(); // despierta un escritor
        oktoread.signalAll(); // despierta todos los lectores
        lock.unlock();
    }

    // Como este método solo lo llamamos cuando se conecta el cliente al servidor, no necesitamos concurrencia
    // pues solo puede existir este proceso en la máquina cliente.
    public ArrayList<String> keyList() {
        ArrayList<String> ret = new ArrayList<>();
        for (String file : id_file.keySet()) {
            ret.add(file);
        }
        return ret;
    }
}




