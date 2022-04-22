package msg;

import data.Data;
import server.DataMonitor;

import java.util.HashMap;
import java.util.Set;

public class UserListACKMessage extends Message {

	// Informacion global que se pasa como mensaje
	private HashMap<String, Set<String>> id_lista;

	public UserListACKMessage(String dest, HashMap<String, Set<String>> id_lista) {
		super("server", dest);
		this.id_lista = id_lista;
	}

	public KindM getType() {
		return KindM.USERLISTACK;
	}

	public String toString() {
		return "UserListACK{" + id_lista.toString() + "}";
	}
}
