package project2;

import java.awt.Graphics;
import java.util.Vector;

public class GameObjectHandler {
	private Vector<GameObject> objects = new Vector<GameObject>();
	protected PlayerChar player;
	public double startX, startY;
	protected int fieldTileX, fieldTileY;
	
	public GameObjectHandler(int x, int y){
		this.fieldTileX = x;
		this.fieldTileY = y;
	}
	public void init(boolean [][] map){
		player = new PlayerChar("Chris",ObjectId.HumanZombie);
		this.addObject(player);	
		this.sendMapToAI(map);
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
		for(GameObject singleObject: objects){
			singleObject.update(objects);
		}
		cam.update(player);
	}
	public void render(Graphics g){
		for(GameObject singleObject: objects){
			singleObject.render(g);
		}
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