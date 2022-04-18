package msg;

public class FileRequestACKMessage extends Message {

	public FileRequestACKMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.FILEREQUESTACK;
	}
}
