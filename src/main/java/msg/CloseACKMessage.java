package msg;

public class CloseACKMessage extends Message {

	public KindM getType() {
        return KindM.CLOSEACK;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
