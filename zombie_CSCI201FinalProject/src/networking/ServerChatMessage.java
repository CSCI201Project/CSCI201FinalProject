package networking;

import java.io.Serializable;

public class ServerChatMessage implements Serializable {
	private static final long serialVersionUID = -6224991516250470573L;
	
	private String recipient;
	private String sender;
	private String message;
	
	public ServerChatMessage(String recipient, String sender, String message) {
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
	}
	
	public String getRecipient() { return this.recipient; }
	public String getSender() { return this.sender; }
	public String getMessage() { return this.message; }
}
