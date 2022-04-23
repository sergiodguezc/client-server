package msg;

public class SendFileMessage extends Message {

	private String file;

	// Nombre del receptor al que tiene que mandar el fichero
	private String receiver;

	public SendFileMessage(String dest, String receiver, String file) {
		super("server", dest);
		this.file = file;
		this.receiver = receiver;
	}

	public KindM getType() {
		return KindM.SENDFILE;
	}

	public String getFile() {
		return file;
	}

	public String getReceiver() {
		return receiver;
	}
}
