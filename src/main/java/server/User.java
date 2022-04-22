package server;

import data.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class User implements Cloneable {

    private final String ip;
    private final String username;
    private final ObjectInputStream fin;
    private final ObjectOutputStream fout;
    private Set<String> descargas;
    
    public User(String ip, String username, ObjectOutputStream fout, ObjectInputStream fin) {
        this.ip = ip;
        this.username = username;
        this.fin = fin;
        this.fout = fout;
        this.descargas = new HashSet<>();
    }

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
        User user = null;
        try {
            user = new User(new String(ip), new String(username), new ObjectOutputStream(fout), new ObjectInputStream(fin));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
    
}
