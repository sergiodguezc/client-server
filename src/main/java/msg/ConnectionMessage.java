package msg;

import java.util.Set;

public class ConnectionMessage extends Message {

	// Ficheros que tiene disponible
	private Set<String> files;

	public ConnectionMessage(String src, String dest, Set<String> files) {
		super(src, dest);
		this.files = files;
	}

	public KindM getType() {
		return KindM.CONNECTION;
	}

	public Set<String> getFiles() {
		return files;
	}
    
}

