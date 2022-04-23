package msg;

public class FileReceivedMessage extends Message {

    private String file;

    public FileReceivedMessage(String src, String file) {
        super(src, "server");
    }

    public KindM getType() {
        return KindM.FILERECEIVED;
    }

    public String getFile() {
        return file;
    }
}
