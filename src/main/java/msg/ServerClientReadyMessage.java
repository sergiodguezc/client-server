package msg;

public class ServerClientReadyMessage extends Message {

	public ServerClientReadyMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.SERVERCLIENTREADY;
	}

}
