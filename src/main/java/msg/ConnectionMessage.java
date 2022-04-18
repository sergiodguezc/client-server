package msg;

public class ConnectionMessage extends Message {

	public ConnectionMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.CONNECTION;
	}
    
}

