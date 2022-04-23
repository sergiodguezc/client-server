package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// MONITOR que resuelve el problema de lectores-escritores
// con la tabla del servidor que almacena la informacion
// disponible en el sistema. Implementamos el monitor
// con un Lock y Variables Condicion.

public class DataMonitor {

    // Utilizamos como estructura de datos una tabla Hash
    // con clave = identificador del archivo
    // y valor = conjunto de usuarios con el archivo

    private HashMap<String, HashSet<String>> id_file;
    private Lock lock;
    private volatile int nr, nw;
    private Condition oktoread, oktowrite;

    public DataMonitor() {
        id_file = new HashMap<>();
        lock = new ReentrantLock(true);
        oktoread = lock.newCondition();
        oktowrite = lock.newCondition();
    }

    // Lectura de un elemento de la tabla
    public HashSet<String> get(String id) {
        requestRead();
        HashSet<String> users = id_file.get(id);
        releaseRead();
        return users;
    }

    // Escritura de un elemento de la tabla
    // Pueden ocurrir dos cosas:
    // - No existe un Data, se crea una nueva entrada
    // - Existe Data, se añade un nuevo user al conjunto de usuarios del data
    public void add(String filename, String u) {
        requestWrite();
        if (!id_file.containsKey(filename)) {
            HashSet<String> users = new HashSet<>();
            users.add(u);
            id_file.put(filename, users);
        } else {
            id_file.get(filename).add(u);
        }
        releaseWrite();
    }

    // Escritura de un elemento de la tabla
    // Pueden ocurrir dos cosas:
    // - El conjunto queda vacía, se elimina la entrada
    // - Sigue habiendo elementos en el conjunto, se elimina solo el usuario del
    // conjunto
    public void delete(String filename, String u) {
        requestWrite();
        id_file.get(filename).remove(u);
        if (id_file.get(filename).isEmpty()) {
            id_file.remove(filename);
        }
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
}
