package weapons;

import project2.ObjectId;

public abstract class Weapon{
	public int currentAmmoCount;
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
	public boolean safetyOn() {
		return !canFire;
	}
	public boolean outOfAmmo() {
		return currentAmmoCount == 0;
	}
	public int getAmmo() {
		return currentAmmoCount;
	}
	public void setAmmo(int cac) {
		this.currentAmmoCount = cac;
	}
	public int getMaxAmmo(){
		return this.maxAmmoCount;
	}
}


