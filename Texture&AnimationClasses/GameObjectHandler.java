package project;

import java.awt.Graphics;
import java.util.Vector;

public class GameObjectHandler {
	private Vector<GameObject> objects = new Vector<GameObject>();
	protected PlayerChar player;
	
	public GameObjectHandler() {
		player = new PlayerChar("Chris");
		player.setPlayerType(GameObject.SURVIVOR);
		this.addObject(player);
	}
	
	public Vector<GameObject> getObjects(){
		return objects;
	}
	
	public PlayerMovement getController(){
		return player.getKeyAdapter();
	}
	
	public void update(){
		for(GameObject singleObject: objects){
			singleObject.update(objects);
		}
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
}
