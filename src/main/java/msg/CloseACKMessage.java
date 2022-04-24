package msg;

public class CloseACKMessage extends Message {

	public CloseACKMessage(String dest) {
		super("server", dest);
	}

	public KindM getType() {
        return KindM.CLOSEACK;
	}

	public String toString() {
		return "Closing connection...";
	}
}
