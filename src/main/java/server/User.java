package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class User {

    private final String ip;
    private final String username;
    private final ObjectInputStream fin;
    private final ObjectOutputStream fout;
    
    public User(String ip, String username, ObjectOutputStream fout, ObjectInputStream fin) {
        this.ip = ip;
        this.username = username;
        this.fin = fin;
        this.fout = fout;
    }
    
}
