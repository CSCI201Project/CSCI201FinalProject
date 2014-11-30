package weapons;

import project2.GameObject;
import project2.ObjectId;

public abstract class Weapon{
	protected int currentAmmoCount;
	protected int maxAmmoCount;
	protected int ammoFromPickup; 
	protected int bulletDamage;
	protected double fireRate;
	protected boolean canFire;
	private ObjectId id;
	
	public Weapon(ObjectId id) {
		this.id = id;
	}
	public ObjectId getId(){
		return id;
	}
	
	public abstract Bullet fireWeapon(int x, int y, String direction);
	public abstract void addAmmo();
	
	public boolean outOfAmmo(){
		return currentAmmoCount == 0;
	}
}


