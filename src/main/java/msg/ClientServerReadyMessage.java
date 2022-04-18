package msg;

public class ClientServerReadyMessage extends Message {

	public ClientServerReadyMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.CLIENTSERVERREADY;
	}
    
}
