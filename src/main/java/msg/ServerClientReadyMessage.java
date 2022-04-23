package msg;

public class ServerClientReadyMessage extends Message {

	private int port;

	public ServerClientReadyMessage(String dest, int port) {
		super("server", dest);
		this.port = port;
	}

	public KindM getType() {
		return KindM.SERVERCLIENTREADY;
	}

	public int getPort() {
		return port;
	}
}
