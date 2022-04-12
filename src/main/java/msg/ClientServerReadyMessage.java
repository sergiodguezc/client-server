package msg;

public class ClientServerReadyMessage extends Message {

	public KindM getType() {
		return KindM.CLIENTSERVERREADY;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
