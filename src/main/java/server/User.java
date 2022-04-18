package server;

import data.Data;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class User {

    private final String ip;
    private final String username;
    private final ObjectInputStream fin;
    private final ObjectOutputStream fout;
    private ArrayList<Data> lista;
    
    public User(String ip, String username, ObjectOutputStream fout, ObjectInputStream fin, ArrayList<Data> lista) {
        this.ip = ip;
        this.username = username;
        this.fin = fin;
        this.fout = fout;
        this.lista = lista;
    }

	public String getUsername() {
		return username;
	}
    
}
