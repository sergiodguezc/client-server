package msg;

public class CloseMessage extends Message {

	public CloseMessage(String src) {
		super(src, "server");
	}

	public KindM getType() {
        return KindM.CLOSE;
	}

}
