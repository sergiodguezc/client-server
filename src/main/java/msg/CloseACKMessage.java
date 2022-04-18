package msg;

public class CloseACKMessage extends Message {

	public CloseACKMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
        return KindM.CLOSEACK;
	}
}
