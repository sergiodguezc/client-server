package msg;

public class ConnectionMessage extends Message {
    private String ip;
    private String username;

    public ConnectionMessage(String ip, String username) {
        this.ip = ip;
        this.username = username;
    }

	public KindM getType() {
		return KindM.CONNECTION;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}

