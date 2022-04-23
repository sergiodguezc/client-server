package msg;

public class ClientServerReadyMessage extends Message {

	private String receiver;
	public ClientServerReadyMessage(String src, String receiver) {
		super(src, "server");
		this.receiver = receiver;
	}

	public KindM getType() {
		return KindM.CLIENTSERVERREADY;
	}
    
}
