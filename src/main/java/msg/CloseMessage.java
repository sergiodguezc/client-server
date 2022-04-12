package msg;

public class CloseMessage extends Message {

	public KindM getType() {
        return KindM.CLOSE;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
