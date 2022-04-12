package msg;

public class UserListMessage extends Message {

	public KindM getType() {
		return KindM.USERLIST;
	}

	public String getSrc() {
		return null;
	}

	public String getDest() {
		return null;
	}
    
}
