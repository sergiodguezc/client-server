package msg;

public abstract class Message {
    public abstract int getType();
    public abstract String getSrc();
    public abstract String getDest();
}
