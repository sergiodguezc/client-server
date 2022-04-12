package msg;

public class FileRequestMessage extends Message {

	public KindM getType() {
		return KindM.FILEREQUEST;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
