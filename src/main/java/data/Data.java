package data;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import server.User;

public class Data implements Cloneable{

    // Lista de usuarios que tienen esta informacion
    private HashSet<String> users;
    private File fichero;

    // Constructor
    public Data(String user, String filename) {
        users = new HashSet<>();
        users.add(user);
        setFichero(new File(filename));
    }

    public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public void addUser(String u) {
        users.add(u);
    }

	public void deleteUser(String u) {
        users.remove(u);
	}

	public boolean isEmpty() {
		return users.isEmpty();
	}

    public Set<String> clone() {
        HashSet<String> view = new HashSet<>();
        for (String u : users) {
            view.add(new String(u));
        }
        return view;
    }

    public String getUser() {
        return (String) users.toArray()[0];
    }
}
