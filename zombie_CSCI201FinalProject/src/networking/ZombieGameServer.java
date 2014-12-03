package networking;

import gui.WaitingScreen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;

import characters.PlayerChar;
import project2.GameObject;
import project2.GameWindow;
import project2.ObjectId;
import weapons.Bullet;

public class ZombieGameServer extends Thread {
	private GameWindow gameFrame;
	private JFrame currentFrame;
	
	private Vector<ZombieThread> ztVector = new Vector<ZombieThread>();
	private ServerSocket ss;
	private boolean done = false;
	private String playerName;
	
	public ZombieGameServer(int port) {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.start();
	}
	
	public void run() {
		try {
			while(!done) {
				System.out.println("Waiting for connections... at " + ss.getInetAddress());
				
				Socket s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress());
				
				s.setTcpNoDelay(true);
				s.setSoTimeout(20000);
				s.setReceiveBufferSize(100000);
				s.setSendBufferSize(100000);
				s.setKeepAlive(true);
				
				ZombieThread zt = new ZombieThread(s, this);
				ztVector.add(zt);
				zt.start();
				
				refreshScreen();
			}
			
			for(ZombieThread zt : ztVector) {
				zt.close();
			}
			ss.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//gui
	public void nextScreen() {
		for(ZombieThread z : ztVector) {
			z.nextScreen();
		}
	}
	public void refreshScreen() {
		WaitingScreen w = (WaitingScreen) currentFrame;
		if(w != null) {
			w.updateConnectedPlayersList();
		}
		
		for(ZombieThread z : ztVector) {
			z.refreshScreen();
		}
	}
	
	//game
	public void updateObjects(Vector<GameObject> gameObjects) {
		ServerBulletList sbl = new ServerBulletList();
		
		for(GameObject g : gameObjects) {
			for(ZombieThread z : ztVector) {
				if(g.getID() == ObjectId.NormalZombie) {
					z.sendUpdatedZombie(g);
				}
			}
			
			if(g.getID() == ObjectId.Bullet) {
				Bullet b = (Bullet) g;
				ServerBullet sb = new ServerBullet(g.getX(), g.getY(), b.getDamage(), b.getDirection());
				sbl.add(sb);
			}
		}
			
		for(ZombieThread z : ztVector) {
			z.sendUpdatedBullets(sbl);
		}
	}
	
	public int getNumberConnections() {
		return ztVector.size();
	}
	public Vector<ZombieThread> getConnections() {
		return ztVector;
	}
	
	public void createBullet(ServerBullet sb) {
		gameFrame.createBulletObject(sb);
	}
	
	public void establishConnection(Socket s) {
		System.out.println("Established connection with " + s.getInetAddress());
	}
	public void removeZombieThread(ZombieThread zt) {
		ztVector.remove(zt);
	}
	
	public void initializePlayer(PlayerChar pc) {
		sendPlayer(pc, true);
	}
	public void syncPlayer(PlayerChar pc) {
		sendPlayer(pc, false);
	}
	public void sendPlayer(PlayerChar pc, boolean init) {
		ServerPlayerObject spo = new ServerPlayerObject(pc.getX(), pc.getY(), pc.getName(), pc.getID(), init);
		spo.setVelX(pc.getVelX());
		spo.setVelY(pc.getVelY());
		
		for(ZombieThread zt : ztVector) {
			zt.createPlayer(spo);
		}
	}
	public void distributeInitPlayer(ServerPlayerObject spo) {
		gameFrame.createPlayer(spo);
		for(ZombieThread zt : ztVector) {
			if(!zt.getPlayerName().equals(spo.getName())) {
				zt.createPlayer(spo);
			}
		}
	}
	public void distributeSyncPlayer(ServerPlayerObject spo) {
		gameFrame.syncPlayer(spo);
		for(ZombieThread zt : ztVector) {
			if(!zt.getPlayerName().equals(spo.getName())) {
				zt.syncPlayer(spo);
			}
		}
	}
	
	public void sendMessageToFrom(String recipient, String sender, String message) {
		for(ZombieThread zt : ztVector)  {
			if(zt.getPlayerName().equals(recipient))
				zt.handleMessage(new ServerChatMessage(recipient, sender, message));
		}
	}
	
	public void setGameFrame(GameWindow gw) {
		this.gameFrame = gw;
	}
	public void setFrame(JFrame f) {
		this.currentFrame = f;
	}
	public JFrame getFrame() {
		return this.currentFrame;
	}

	public void setPlayerName(String pn) {
		this.playerName = pn;
	}
	public String getPlayerName() {
		return this.playerName;
	}
	
	public Vector<String> getPlayerNames() {
		Vector<String> pn = new Vector<String>();
		
		pn.add(getPlayerName());
		for(ZombieThread zt : ztVector) {
			if(zt.getPlayerName() != null)
				pn.add(zt.getPlayerName());
		}
		
		return pn;
	}
	
	public void close() {
		done = true;
	}
	
	public static void main(String[] args) {
		new ZombieGameServer(100);
	}
}
