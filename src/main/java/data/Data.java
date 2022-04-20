package data;

import java.io.File;
import java.util.HashSet;

import server.User;

public class Data {

    // Lista de usuarios que tienen esta informacion
    private HashSet<User> users;
    private File fichero;

    // Constructor
    public Data(User u, String filename) {
        users = new HashSet<>();
        users.add(u);
        setFichero(new File(filename));
    }

    public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public void addUser(User u) {
        users.add(u);
    }

	public void deleteUser(User u) {
        users.remove(u);
	}

	public boolean isEmpty() {
		return users.isEmpty();
	}
}
