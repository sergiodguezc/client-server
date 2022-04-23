package msg;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Message implements Serializable {
    private String src;
    private String dest;

    public Message(String src, String dest) {
        this.src = src;
        this.dest = dest;
    }

    public abstract KindM getType();
    public String getFile() {
        return "";
    }

    public String getReceiver() {
        return "";
    }

    public String toString() {return "";};

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public int getPort() {
        return 0;
    }

    public ArrayList<String> getFiles() {
        return null;
    }
}
