package project2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import weapons.Bullet;
import characters.AIChar;
import characters.PlayerChar;
import networking.ChatClient;
import networking.ServerBullet;
import networking.ServerBulletList;
import networking.ServerPlayerObject;
import networking.ServerZombieObject;
import networking.ZombieGameClient;
import networking.ZombieGameServer;

public class GameWindow extends JFrame {
	private PlayingField pf;
//	public JFrame frame = this;
	
	private boolean isHost;
	private ZombieGameServer server;
	private ZombieGameClient client;
	
	public GameWindow(PlayingField pf){
		this.setLayout(new BorderLayout());
		this.setSize(1000,600);
		this.setLocation(100, 50);
		this.setMinimumSize(new Dimension(800,600));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ChatClient chatPanel = new ChatClient("localhost", 5555);
//		chatPanel.add(label);
//		chatPanel.setPreferredSize(new Dimension(300,300));
//		this.add(chatPanel);
		
		System.out.println("width and height: " + this.getWidth() + " " + this.getPreferredSize().height);
		chatPanel.setPreferredSize(new Dimension(300,this.getHeight()));
//		chatPanel.setPreferredSize(new Dimension(300,600));
		this.add(chatPanel, BorderLayout.EAST);
		
		this.pf = pf;
		this.add(this.pf); 
		
		this.setVisible(true);
		
		Thread t = new Thread(pf);
		t.start();
		
		this.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(isHost) server.close();
				else client.close();
			}

			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});
	}
	
	public void setServer(ZombieGameServer zgs) {
		this.isHost = true;
		this.server = zgs;
		this.server.setGameFrame(this);
		
		pf.setServer(this.server);
	}
	public void setClient(ZombieGameClient zgc, ZombieGameServer zgs) {
		this.isHost = false;
		this.client = zgc;
		this.client.setGameFrame(this);
		this.server = zgs;
		
		pf.setClient(this.client, this.server);
	}
	
	public void updateZombieGameObject(ServerZombieObject szo) {
		if(!isHost) {
			Vector<GameObject> gs = pf.getGameObjectHandler().getObjects();
			Iterator<GameObject> iterator = gs.iterator();
			
			while(iterator.hasNext()) {
				GameObject g = iterator.next();
				if(g instanceof AIChar) {
					AIChar ai = (AIChar) g;
					if(ai.iterator == szo.getIterator()) {
						g.setX(szo.getX());
						g.setY(szo.getY());
						ai.setHealth(szo.getHealth());
						
						if(szo.getHealth() <= 0)
							ai.turnOff();
						
						//System.out.println(szo.getHealth());
					}
				}
			}
		}
	}
	public void updateBulletObjects(ServerBulletList sbl) {
		if(!isHost) {
			int i = 0;
			
			Vector<GameObject> bullets = pf.getGameObjectHandler().getBullets();
			Vector<GameObject> tempBullets = pf.getGameObjectHandler().getTempBullets();
			for(ServerBullet b : sbl.getServerBullets()) {
				if(i < bullets.size()) {
					bullets.get(i).setX(b.x);
					bullets.get(i).setY(b.y);
					i++;
				} else {
					tempBullets.add(new Bullet(ObjectId.Bullet, (int) b.x, (int) b.y, b.getDamage(), false, b.getDirection()));
					i++;
				}
			}
		}
	}
	public void createBulletObject(ServerBullet sb) {
		if(isHost) {
			//System.out.println(sb.x + ", " + sb.y);
			pf.getGameObjectHandler().addBullets(new Bullet(ObjectId.Bullet, (int) sb.x, (int) sb.y, sb.getDamage(), false, sb.getDirection()));
		}
	}
	
	public void createPlayer(ServerPlayerObject spo) {
		pf.getGameObjectHandler().addPlayer(new PlayerChar(spo.getName(), spo.getX(), spo.getY(), ObjectId.HumanSurvivor));
	}
	public void syncPlayer(ServerPlayerObject spo) {
		for(PlayerChar pc : pf.getGameObjectHandler().getPlayers()) {
			if(pc.getName() == spo.getName()) {
				pc.setX(spo.getX());
				pc.setY(spo.getY());
				
				pc.setVelX(spo.getVelX());
				pc.setVelY(spo.getVelY());
			}
		}
	}

	public static void main(String[] args){
		ZombieGameServer zgs = new ZombieGameServer(80);
		zgs.setPlayerName("Chris");
		ZombieGameClient zgc1 = new ZombieGameClient("localhost", 80);
		zgc1.setPlayerName("Fuckboy");
//		ZombieGameClient zgc2 = new ZombieGameClient("localhost", 80);
//		zgc2.setPlayerName("Buster");
		
		GameWindow g1 = new GameWindow(new PlayingField());
		g1.setServer(zgs);
		GameWindow g2 = new GameWindow(new PlayingField());
		g2.setClient(zgc1, zgs);
//		GameWindow g3 = new GameWindow(new PlayingField());
//		g3.setClient(zgc2, zgs);
	}
}


