package project2;

import java.awt.Graphics;
import java.util.Vector;

public class GameObjectHandler {
	private Vector<GameObject> objects = new Vector<GameObject>();
	protected PlayerChar player;
	
<<<<<<< HEAD
	public GameObjectHandler() {
		player = new PlayerChar("Chris");
		player.setPlayerType(GameObject.SURVIVOR);
=======
	public GameObjectHandler(int x, int y){
		this.fieldTileX = x;
		this.fieldTileY = y;
		
	}
	public void init(){
		createLevel();
		player = new PlayerChar("Chris",ObjectId.HumanSurvivor);
>>>>>>> 48a95a37cdac62f4478f140347d260d44e914ce4
		this.addObject(player);
		
		
	}
	public void createLevel(){
		for(int i = 0; i < 10; i++){
			this.addObject(new AIChar(i*fieldTileX, 20, ObjectId.NormalZombie));
		}
	}
	
	public Vector<GameObject> getObjects(){
		return objects;
	}
	
	public PlayerMovement getController(){
		return player.getKeyAdapter();
	}
<<<<<<< HEAD
	
	public void update(){
=======
	public void update(Camera cam){
		
>>>>>>> 48a95a37cdac62f4478f140347d260d44e914ce4
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
