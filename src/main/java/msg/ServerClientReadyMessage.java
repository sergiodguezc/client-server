package msg;

public class ServerClientReadyMessage extends Message {

	private int port;

	public ServerClientReadyMessage(String dest, int port) {
		super("server", dest);
	}

	public KindM getType() {
		return KindM.SERVERCLIENTREADY;
	}

	public int getPort() {
		return port;
	}
}
