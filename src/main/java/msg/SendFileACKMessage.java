package msg;

public class SendFileACKMessage extends Message {

	public KindM getType() {
		return KindM.SENDFILEACK;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
