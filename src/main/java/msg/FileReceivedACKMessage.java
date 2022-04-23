package msg;

public class FileReceivedACKMessage extends Message{

    public FileReceivedACKMessage(String dest) {
        super("server", dest);
    }

    public KindM getType() {
        return KindM.FILERECEIVEDACK;
    }
}
