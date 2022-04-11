package msg;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public abstract int getType();
    public abstract String getSrc();
    public abstract String getDest();
}
