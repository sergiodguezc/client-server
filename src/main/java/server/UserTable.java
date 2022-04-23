package server;

// Clase que encapsula el problema de lectores-escritores
// con la tabla del servidor que almacena la informacion
// de los usuarios del sistema, resuelto con SEMAFOROS

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class UserTable {
    // Utilizamos como estructura de datos una tabla Hash
    // con clave = username
    // y valor = User
    private HashMap<String, User> id_user;

    // Implementacion del problema del lector escritor mediante paso de testigo.
    private volatile int nr, nw, dr, dw;
    private Semaphore e, r, w;

    public UserTable() {
        id_user = new HashMap<>();
        nr = 0; // RW: (nr==0 or nw == 0) and nw <= 1
        nw = 0;
        dr = 0; // numero de lectores retrasados
        dw = 0; // numero de escritores retrasados
        e = new Semaphore(1); // seccion critica
        r = new Semaphore(0); // retrasar readers
        w = new Semaphore(0); // retrasar writers
    }

    // Como el método puede devolver null, cada vez que se llame a esta función
    // hay que comprobar que el resultado sea distinto de null.
    public User get(String username) {
        User u = null;
        try {
            e.acquire();
            if (nw > 0) {
                dr = dr + 1;
                e.release();
                r.acquire();
            }
            nr = nr + 1;
            if (dr > 0) {
                dr = dr - 1;
                r.release();
            } else
                e.release();
            
            // READ
            u = id_user.get(username);

            e.acquire();
            nr = nr - 1;
            if (nr == 0 && dw > 0) {
                dw = dw - 1;
                w.release();
            } else
                e.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return u;
    }

    public HashMap<String,User> getAll() {
        HashMap<String,User> view = null;
        try {
            e.acquire();
            if (nw > 0) {
                dr = dr + 1;
                e.release();
                r.acquire();
            }
            nr = nr + 1;
            if (dr > 0) {
                dr = dr - 1;
                r.release();
            } else
                e.release();

            // COPIA
            view = clone();

            e.acquire();
            nr = nr - 1;
            if (nr == 0 && dw > 0) {
                dw = dw - 1;
                w.release();
            } else
                e.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }


    // Escritor que añade un elemento
    public void add(User u) {
        try {
            e.acquire();
            if (nr > 0 || nw > 0) {
                dw = dw + 1;
                e.release();
                w.acquire();
            }
            nw = nw + 1;
            e.release();

            // WRITE
            id_user.put(u.getUsername(), u);

            e.acquire();
            nw = nw - 1;
            if (dr > 0) {
                dr = dr - 1;
                w.release();
            } else if (dw > 0) {
                dw = dw - 1;
                w.release();
            } else
                e.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Escritor que elimina un elemento
    public void delete(String u) {
        try {
            e.acquire();
            if (nr > 0 || nw > 0) {
                dw = dw + 1;
                e.release();
                w.acquire();
            }
            nw = nw + 1;
            e.release();

            // WRITE
            id_user.remove(u);

            e.acquire();
            nw = nw - 1;
            if (dr > 0) {
                dr = dr - 1;
                w.release();
            } else if (dw > 0) {
                dw = dw - 1;
                w.release();
            } else
                e.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Obtener copia de la tabla
    public HashMap<String, User> clone() {
        HashMap<String,User> view = new HashMap<>();
        for(String username : id_user.keySet())
            view.put(new String(username), id_user.get(username).clone());
        return view;
    }
}
