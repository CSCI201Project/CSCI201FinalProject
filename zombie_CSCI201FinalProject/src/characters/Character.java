package characters;

import project2.GameObject;
import project2.ObjectId;

public abstract class Character extends GameObject{
	protected int health = 100;
	public Character(ObjectId id) {
		super(id);
	}
	public int getHealth(){
		return health;
	}
	public void setHealth(int h) {
		health = h;
	}
	public void takeDamage(int damage){
		health = health - damage;
	}
	
}
