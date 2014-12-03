package networking;

import gui.Nextable;
import gui.WaitingScreen;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;

import characters.PlayerChar;
import project2.GameObject;
import project2.GameWindow;
import weapons.Bullet;

public class ZombieGameClient extends Thread {
	Socket s;
	Vector<GameObject> zombieList = new Vector<GameObject>();
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JFrame currentFrame;
	private GameWindow gameFrame;
	private String playerName;
	
	private boolean done = false;
	
	public ZombieGameClient(String hostname, int port) {
		try {
			this.s = new Socket(hostname, port);
			System.out.println("Trying to connect at " + s.getInetAddress());
			
			this.oos = new ObjectOutputStream(s.getOutputStream());
			
			this.start();
		}
		catch(IOException ioe) {
			System.out.println("ioe in zombie client: " + ioe.getMessage());
		}
	}
	
	public void start() {
		super.start();
		
		try {
			System.out.println("writing to server");
			oos.writeObject(new String("CONNECT"));
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe trying to start zombie client: " + ioe.getMessage());
		}
	}
	
	public void generateBullet(Bullet b) {
		try {
			ServerBullet sb = new ServerBullet(b.getX(), b.getY(), b.getDamage(), b.getDirection());
			oos.writeObject(sb);
			oos.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void initializePlayer(PlayerChar pc) {
		sendPlayer(pc, true);
	}
	public void syncPlayer(PlayerChar pc) {
		sendPlayer(pc, false);
	}
	private void sendPlayer(PlayerChar pc, boolean init) {
		try {
			ServerPlayerObject spo = new ServerPlayerObject(pc.getX(), pc.getY(), pc.getName(), pc.getID(), init);
			spo.setVelX(pc.getVelX());
			spo.setVelY(pc.getVelY());
			oos.writeObject(spo);
			oos.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		try {
			this.ois = new ObjectInputStream(s.getInputStream());
			
			while(!done) {
				Object o = ois.readObject();
				if(o != null) {
					if(o instanceof ServerChatMessage) {
						ServerChatMessage scm = (ServerChatMessage) o;
						if(o != null) {
							gameFrame.receiveMessageFrom(scm.getSender(), scm.getMessage());
						}
					}
					
					if(o instanceof ServerZombieObject) {
						ServerZombieObject szo = (ServerZombieObject) o;
						if(szo != null) {
							gameFrame.updateZombieGameObject(szo);
						}
					}
					
					if(o instanceof ServerPlayerObject) {
						ServerPlayerObject spo = (ServerPlayerObject) o;
						if(spo != null) {
							if(spo.getInit()) {
								gameFrame.createPlayer(spo);
							} else {
								gameFrame.syncPlayer(spo);
							}
						}
					}

					if(o instanceof ServerBulletList) {
						ServerBulletList v = (ServerBulletList) o;
						gameFrame.updateBulletObjects(v);
					}
					
					if(o instanceof String) {
						String messageFromServer = (String) o;
						
						if(messageFromServer != null) {
							if(messageFromServer.equals("CONNECT")) {}
							else if(messageFromServer.equals("NEXTSCREEN")) {
								Nextable w = (Nextable) currentFrame; 
								w.nextScreen();
							}
							else if(messageFromServer.equals("REFRESHSCREEN")) {
								WaitingScreen w = (WaitingScreen) currentFrame;
								if(w != null) {
									w.updateConnectedPlayersList();
								}
							}
						}
					}
				}
			}
			
			oos.writeObject(new String("CLOSE"));
			oos.flush();
			
			oos.close();
			ois.close();
			s.close();
		}
		catch(IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setFrame(JFrame f) {
		this.currentFrame = f;
	}
	public void setGameFrame(GameWindow gw) {
		this.gameFrame = gw;
	}
	
	public void setPlayerName(String pn) {
		this.playerName = pn;
	}
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void close() {
		done = true;
	}
	
	public static void main(String[] args) {
		String hostname = "localhost";
		new ZombieGameClient(hostname, 100);
	}
}
