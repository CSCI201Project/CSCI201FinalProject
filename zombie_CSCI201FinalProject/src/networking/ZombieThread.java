package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import characters.AIChar;
import characters.PlayerChar;
import project2.GameObject;

public class ZombieThread extends Thread {
	private Socket s;
	private ZombieGameServer zgs;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private boolean done = false;
	private String playerName = null;
	
	public ZombieThread(Socket s, ZombieGameServer zgs) {
		this.s = s;
		this.zgs = zgs;
		try {
			this.oos = new ObjectOutputStream(s.getOutputStream());
		}
		catch(IOException ioe) {
			System.out.println("ioe in zombie thread: " + ioe.getMessage());
		}
	}
	
	public void setPlayerName(String pn) {
		this.playerName = pn;
	}
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void createPlayer(ServerPlayerObject spo) {
		syncPlayer(spo);
	}
	public void syncPlayer(ServerPlayerObject spo) {
		try {
			oos.writeObject(spo);
			oos.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void nextScreen() {
		try {
			oos.writeObject(new String("NEXTSCREEN"));
			oos.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendUpdatedZombie(GameObject g) {
		try {
			AIChar ai = (AIChar) g;
			oos.writeObject(new ServerZombieObject(g.getX(), g.getY(), ai.getHealth(), g.iterator));
			oos.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void sendUpdatedBullets(ServerBulletList sbl) {
		try {			
			oos.writeObject(sbl);
			oos.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void close() {
		done = true;
	}
	
	public void run() {
		try {
			this.ois = new ObjectInputStream(s.getInputStream());
			
			while(!done) {
				Object o = ois.readObject();
				if(o != null) {
					if(o instanceof ServerBullet) {						
						ServerBullet sb = (ServerBullet) o;
						zgs.createBullet(sb);
					}
					
					if(o instanceof ServerPlayerObject) {
						ServerPlayerObject spo = (ServerPlayerObject) o;
						if(spo.getInit()) {
							setPlayerName(spo.getName());
							zgs.distributeInitPlayer(spo);
						} else {
							zgs.distributeSyncPlayer(spo);
						}
					}
					
					if(o instanceof String) {
						String messageFromClient = (String) o;
						if(messageFromClient != null) {
							if(messageFromClient.equals("CONNECT")) {
								zgs.establishConnection(this.s);
							}
							else if(messageFromClient.equals("CLOSE")) {
								zgs.close();
							}
						}
					}
				}				
			}
			
			ois.close();
			oos.close();
			s.close();
		}
		catch(IOException ioe) {
			zgs.removeZombieThread(this);
			System.out.println("client disconnected from " + s.getInetAddress());
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
