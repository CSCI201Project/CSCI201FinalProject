package project2;

import imageProcessing.Camera;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.Vector;

import weapons.Bullet;
import characters.AIChar;
import characters.PlayerChar;
import characters.PlayerMovement;

public class GameObjectHandler {
	private Vector<GameObject> objects = new Vector<GameObject>();
	private Vector<GameObject> bullets = new Vector<GameObject>();
	protected PlayerChar player;
	public double startX, startY;
	protected int fieldTileX, fieldTileY;
	
	public GameObjectHandler(int x, int y){
		this.fieldTileX = x;
		this.fieldTileY = y;
	}
	public void init(boolean [][] map){
		player = new PlayerChar("Chris",startX, startY,ObjectId.HumanSurvivor);
		this.addObject(player);
		this.sendMapToAI(map);
		player.setHandler(this);
	}
	public void sendMapToAI(boolean [][] map){
		for(int i = 0; i < this.objects.size(); i ++){
			if(objects.get(i).id == ObjectId.NormalZombie ||objects.get(i).id == ObjectId.NormalZombie){
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
	public PlayerMovement getController(){
		return player.getKeyAdapter();
	}
	public void update(Camera cam){
		for(Iterator<GameObject> iterator = objects.iterator(); iterator.hasNext();) {
			GameObject object = iterator.next();
			if (!object.isOn){iterator.remove();};
			object.update(objects);
		}
		cam.update(player);
	}
	public void render(Graphics g){
		objects.addAll(bullets);
		for(GameObject singleObject: objects){
			singleObject.render(g);
		}
	}
	public void addBullets(Bullet b){
		bullets.clear();
		bullets.add(b);
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