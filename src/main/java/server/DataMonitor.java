package server;

import data.Data;

import java.io.File;
import java.util.HashMap;
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
    // y valor = archivo

    private HashMap<String, Data> id_file;
    // TODO: preguntar si usar nuestros locks o los de java
    private Lock lock;
    private volatile int nr, nw;
    private Condition oktoread, oktowrite;

    public DataMonitor() {
        id_file = new HashMap<>();
        lock = new ReentrantLock(true);
        oktoread = lock.newCondition();
        oktowrite = lock.newCondition();
    }

    // Lectura de toda la tabla
    public HashMap<String,Data> getAll() {
        requestRead();
        HashMap<String,Data> t = id_file;
        releaseRead();
        return t;
    }

    // Lectura de un elemento de la tabla
    public Data get(String id) {
        requestRead();
        Data d = id_file.get(id);
        releaseRead();
        return d;
    }

    // Escritura de un elemento de la tabla
    // Pueden ocurrir dos cosas: 
    // - No existe un Data, se crea una nueva entrada
    // - Existe Data, se añade un nuevo user a la lista de usuarios del data
    public void add(User u, Data d) {
    }
    
    // Escritura de un elemento de la tabla
    // Pueden ocurrir dos cosas: 
    // - La lista queda vacía, se elimina la entrada
    // - Sigue habiendo elementos en la lista, se elimina solo el usuario de la lista
    public void delete(User u, Data d) {
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
