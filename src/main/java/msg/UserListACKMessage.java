package msg;

import server.User;

import java.util.ArrayList;
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

	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();
		for (String u : id_user.keySet()) {
			users.add(id_user.get(u));
		}
		return users;
	}
}
