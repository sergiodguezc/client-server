package msg;

public class UserListMessage extends Message {

	public UserListMessage(String src) {
		super(src, "server");
	}

	public KindM getType() {
		return KindM.USERLIST;
	}
}
