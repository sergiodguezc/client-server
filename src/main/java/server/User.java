package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class User implements Cloneable, Serializable {

    private final String ip;
    private final String username;
    private final ObjectInputStream fin;

    // Para proteger el canal fout, que se accede desde otros ClientListener
    // Cuando hay una comunicacion p2p, utilizamos un Lock
    private Lock lock;

    private final ObjectOutputStream fout;
    private Set<String> descargas;
    
    public User(String ip, String username, ObjectOutputStream fout, ObjectInputStream fin) {
        this.ip = ip;
        this.username = username;
        this.fin = fin;
        this.fout = fout;
        this.descargas = new HashSet<>();
        // Iniciamos el lock
        lock = new ReentrantLock(true);
    }

    // Constructor para la copia de la tabla id_user, necesitamos objetos serializables
    public User(String ip, String username) {
        this.ip = ip;
        this.username = username;
        fin = null;
        fout = null;
        this.descargas = new HashSet<>();
    }

    public String getIp() {return ip;}

	public String getUsername() {
		return username;
	}

    public void addData(String d) {
        descargas.add(d);
    }

    public void removeData(String d) {
        descargas.remove(d);
    }

    public User clone() {
        User user = new User(new String(ip), new String(username));

        for(String files : descargas)
            user.addData(new String(files));

        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "ip='" + ip + '\'' +
                ", username='" + username + '\'' +
                ", descargas=" + descargas +
                '}';
    }

    public Set<String> getDescargas() {
        return descargas;
    }

    public ObjectOutputStream getFout() {
        return fout;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
