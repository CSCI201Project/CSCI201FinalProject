package networking;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {

	private ChatThread ct1;
	private ChatThread ct2;
	private Vector<ChatThread> ctVector = new Vector<ChatThread>();
	public Server(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);

			System.out.println("Waiting for connections...");
			Socket s1 = ss.accept();
			System.out.println("Connection from " + s1.getInetAddress());
			ct1 = new ChatThread(s1, this);
			Socket s2 = ss.accept();
			System.out.println("Connection from " + s2.getInetAddress());
			ct2 = new ChatThread(s2, this);
			//				ctVector.add(ct);
			//				ct.start();
			ct1.start();
			ct2.start();
			ct1.send("CONNECTED");
			ct2.send("CONNECTED");
			System.out.println("BOTH clients connected");

		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(String message, ChatThread ct) {
		if (!ct1.equals(ct)) {
			ct1.send(message);

		}
		if(!ct2.equals(ct)){
			ct2.send(message);
		}

	}

	public void removeChatThread(ChatThread ct) {
		ctVector.remove(ct);
	}

	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("What port? ");
		int port = scan.nextInt();
		new Server(port);
	}
}