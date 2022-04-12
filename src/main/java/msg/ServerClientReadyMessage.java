package msg;

public class ServerClientReadyMessage extends Message {

	public KindM getType() {
		return KindM.SERVERCLIENTREADY;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
