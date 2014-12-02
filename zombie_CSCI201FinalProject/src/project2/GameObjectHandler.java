package project2;

import imageProcessing.Camera;

import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import networking.ServerBullet;
import weapons.Bullet;
import characters.AIChar;
import characters.PlayerChar;
import characters.PlayerMovement;

public class GameObjectHandler {
	private Vector<GameObject> objects = new Vector<GameObject>();
	private Vector<GameObject> bullets = new Vector<GameObject>();
	private Vector<PlayerChar> players = new Vector<PlayerChar>();
	protected PlayerChar player;
	public double startX, startY;
	protected int fieldTileX, fieldTileY;

	private Vector<GameObject> tempBullets = new Vector<GameObject>();
	private PlayingField parent;
	public boolean isHost;
	
	public GameObjectHandler(int x, int y, PlayingField parent){
		this.fieldTileX = x;
		this.fieldTileY = y;
		this.parent = parent;
	}
	public void init(boolean [][] map){
		player = new PlayerChar(parent.getPlayerName(), startX, startY, ObjectId.HumanSurvivor);		
		this.addObject(player);
		this.sendMapToAI(map);
		player.setHandler(this);
		
		parent.initializePlayer(player);
	}
	public void sendMapToAI(boolean [][] map){
		for(int i = 0; i < this.objects.size(); i ++){
			if(objects.get(i).id == ObjectId.NormalZombie || objects.get(i).id == ObjectId.NormalZombie){
				AIChar ai = (AIChar) objects.get(i);
				ai.setMap(map);
				ai.setTarget(player);
			}
		}
	}
	public void setStart(double x, double y){
		this.startX = x;
		this.startY = y;
	}
	public void startAI(){		
		for(int i = 0; i < this.objects.size(); i ++){
			if(objects.get(i).id == ObjectId.NormalZombie ||objects.get(i).id == ObjectId.AlphaZombie){
				AIChar ai = (AIChar) objects.get(i);
				Thread t = new Thread(ai);
				t.start();
			}
		}
	}
	public Vector<GameObject> getObjects(){
		return objects;
	}
	public Vector<PlayerChar> getPlayers() {
		return players;
	}
	public Vector<GameObject> getBullets() {
		return bullets;
	}
	public Vector<GameObject> getTempBullets() {
		return tempBullets;
	}
	
	public PlayerMovement getController(){
		return player.getKeyAdapter();
	}
	public void update(Camera cam) {
		//update ai targets
		if(isHost) {
			for(int i = 0; i < this.objects.size(); i ++) {
				if(objects.get(i).id == ObjectId.NormalZombie || objects.get(i).id == ObjectId.NormalZombie) {
					AIChar ai = (AIChar) objects.get(i);
					
					PlayerChar tmpTarget = player;
					double distanceSqr = Math.pow(player.getX() - ai.getX(), 2)
							+ Math.pow(player.getY() - ai.getY(), 2);
					
					Vector<PlayerChar> allPlayers = new Vector<PlayerChar>();
					allPlayers.addAll(players);
					allPlayers.add(player);
					for(PlayerChar p : allPlayers) {
						double tmpDistanceSqr = Math.pow(p.getX() - ai.getX(), 2)
								+ Math.pow(p.getY() - ai.getY(), 2);
						
						if(tmpDistanceSqr < distanceSqr) {
							distanceSqr = tmpDistanceSqr;
							tmpTarget = p;
						}
					}
					
					ai.setTarget(tmpTarget);
				}
			}
		}
		
		//bullets
		if(tempBullets.size() > 0) {
			bullets.addAll(tempBullets);
			tempBullets.clear();
		}
		
		for(Iterator<GameObject> it = bullets.iterator(); it.hasNext();) {
			GameObject b = it.next();
			if(!b.isOn) { it.remove(); continue; }
			b.update(objects);
		}
		
		//objects
		for(Iterator<GameObject> iterator = objects.iterator(); iterator.hasNext();) {
			GameObject object = iterator.next();
			if(!object.isOn) { iterator.remove(); continue; }
			object.update(objects);
		}
		
		//players
		for(Iterator<PlayerChar> iterator = players.iterator(); iterator.hasNext();) {
			PlayerChar player = iterator.next();
			if(!player.isOn) { iterator.remove(); continue; }	
			player.updateStates(objects);
		}
		
		cam.update(player);
		parent.syncPlayer(player);
	}
	public void render(Graphics g) {
		for(GameObject bullet : bullets) {
			bullet.render(g);
		}
		
		for(GameObject singleObject : objects){
			singleObject.render(g);
		}
		
		for(PlayerChar player : players) {
			player.render(g);
		}
	}
	public void addBullets(Bullet b) {
		tempBullets.add(b);
		
		if(!isHost) {
			if(parent != null && b != null)
				parent.transferBullet(b);
		}

		//b.iterator = bullets.size() - 1;
	}
	public void addPlayer(PlayerChar pc) {
		//this.objects.add(pc);
		this.players.add(pc);
	}
	public void addObject(GameObject go){
		this.objects.add(go);
	}
	public void removeObject(GameObject go){
		this.objects.remove(go);
	}
	public int size(){
		return this.objects.size();
	}
}