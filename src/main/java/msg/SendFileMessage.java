package msg;

public class SendFileMessage extends Message {

	public KindM getType() {
		return KindM.SENDFILE;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
