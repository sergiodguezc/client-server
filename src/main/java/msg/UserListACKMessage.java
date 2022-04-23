package msg;

import server.User;

import java.util.HashMap;

public class UserListACKMessage extends Message {

	// Informacion global que se pasa como mensaje
	private HashMap<String, User> id_user;

	public UserListACKMessage(String dest, HashMap<String, User> id_user) {
		super("server", dest);
		this.id_user = id_user;
	}

	public KindM getType() {
		return KindM.USERLISTACK;
	}

	public String toString() {
		return "UserListACK{" + id_user.toString() + "}";
	}
}
