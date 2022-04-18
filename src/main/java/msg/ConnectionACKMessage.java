package msg;

public class ConnectionACKMessage extends Message {

	public ConnectionACKMessage(String dest) {
		super("server", dest);
	}

	public KindM getType() {
        return KindM.CONNECTIONACK;
	}
}
