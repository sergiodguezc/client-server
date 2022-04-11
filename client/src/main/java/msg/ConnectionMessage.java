package msg;

public class ConnectionMessage extends Message {
    private String ip;
    private String username;

    public ConnectionMessage(String ip, String username) {
        this.ip = ip;
        this.username = username;
    }

	public int getType() {
		return 0;
	}

	@Override
	public String getSrc() {
		return null;
	}

	@Override
	public String getDest() {
		return null;
	}
    
}

