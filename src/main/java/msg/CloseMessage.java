package msg;

public class CloseMessage extends Message {

	public CloseMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
        return KindM.CLOSE;
	}

}
