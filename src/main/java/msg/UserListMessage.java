package msg;

public class UserListMessage extends Message {

	public UserListMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.USERLIST;
	}
}
