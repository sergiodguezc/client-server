package msg;

public class ConnectionACKMessage extends Message {

	public ConnectionACKMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
        return KindM.CONNECTIONACK;
	}
}
