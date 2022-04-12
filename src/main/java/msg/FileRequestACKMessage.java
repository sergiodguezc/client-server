package msg;

public class FileRequestACKMessage extends Message {

	public KindM getType() {
		return KindM.FILEREQUESTACK;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
