package msg;

public class ConnectionACKMessage extends Message {

	public KindM getType() {
        return KindM.CONNECTIONACK;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
