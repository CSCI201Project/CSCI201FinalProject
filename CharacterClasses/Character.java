package project2;

public abstract class Character extends GameObject{
	public Character(ObjectId id) {
		super(id);
	}
	protected int health;
	protected int width = 32, height = 64;	
}
