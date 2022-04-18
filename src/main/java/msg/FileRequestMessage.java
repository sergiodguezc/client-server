package msg;

public class FileRequestMessage extends Message {

	public FileRequestMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.FILEREQUEST;
	}
    
}
