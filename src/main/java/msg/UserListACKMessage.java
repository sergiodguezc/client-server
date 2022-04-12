package msg;

public class UserListACKMessage extends Message {

	public KindM getType() {
		return KindM.USERLISTACK;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
