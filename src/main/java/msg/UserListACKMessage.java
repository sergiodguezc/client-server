package msg;

public class UserListACKMessage extends Message {

	public UserListACKMessage(String src, String dest) {
		super(src, dest);
	}

	public KindM getType() {
		return KindM.USERLISTACK;
	}

}
