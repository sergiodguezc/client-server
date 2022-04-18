package msg;

public class SendFileACKMessage extends Message {

	public SendFileACKMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.SENDFILEACK;
	}
}
