package chat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class server {

	private thread ct1;
	private thread ct2;
	private Vector<thread> ctVector = new Vector<thread>();
	public server(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);

			System.out.println("Waiting for connections...");
			Socket s1 = ss.accept();
			System.out.println("Connection from " + s1.getInetAddress());
			ct1 = new thread(s1, this);
			Socket s2 = ss.accept();
			System.out.println("Connection from " + s2.getInetAddress());
			ct2 = new thread(s2, this);
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

	public void sendMessage(String message, thread ct) {
		if (!ct1.equals(ct)) {
			ct1.send(message);

		}
		if(!ct2.equals(ct)){
			ct2.send(message);
		}

	}

	public void removeChatThread(thread ct) {
		ctVector.remove(ct);
	}

	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("What port? ");
		int port = scan.nextInt();
		new server(port);
	}
}