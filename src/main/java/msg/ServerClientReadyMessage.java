package msg;

public class ServerClientReadyMessage extends Message {

	public ServerClientReadyMessage(String dest) {
		super("server", dest);
	}

	public KindM getType() {
		return KindM.SERVERCLIENTREADY;
	}

}
