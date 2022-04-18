package msg;

public class SendFileMessage extends Message {

	public SendFileMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.SENDFILE;
	}
}
