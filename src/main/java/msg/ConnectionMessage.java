package msg;

import java.util.ArrayList;

public class ConnectionMessage extends Message {

	// Ficheros que tiene disponible
	private ArrayList<String> files;

	public ConnectionMessage(String src, String dest, ArrayList<String> files) {
		super(src, dest);
		this.files = files;
	}

	public KindM getType() {
		return KindM.CONNECTION;
	}

	public ArrayList<String> getFiles() {
		return files;
	}
    
}

