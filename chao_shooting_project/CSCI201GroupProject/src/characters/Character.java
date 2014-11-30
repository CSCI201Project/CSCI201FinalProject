package characters;

import project2.GameObject;
import project2.ObjectId;

public abstract class Character extends GameObject{
	protected int health;
	public Character(ObjectId id) {
		super(id);
	}
	public void takeDamage(int damage){
		health = health - damage;
	}
	
}
