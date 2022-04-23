package msg;

public class FileRequestMessage extends Message {
	private String file;

	public FileRequestMessage(String src, String file) {
		super(src, "server");
	}

	public KindM getType() {
		return KindM.FILEREQUEST;
	}

	public String getFile() {
		return file;
	}
    
}
