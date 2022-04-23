package msg;

public class ClientServerReadyMessage extends Message {

	private String receiver;
	private int port;

	public ClientServerReadyMessage(String src, String receiver, int port) {
		super(src, "server");
		this.receiver = receiver;
		this.port = port;
	}

	public KindM getType() {
		return KindM.CLIENTSERVERREADY;
	}

	public int getPort() {
		return port;
	}
}
