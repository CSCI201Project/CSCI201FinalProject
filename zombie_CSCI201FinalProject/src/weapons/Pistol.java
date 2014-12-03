package weapons;

import project2.ObjectId;

public class Pistol extends Weapon{
	public Pistol(ObjectId id) {
		super(id);
		this.currentAmmoCount = 30;
		this.maxAmmoCount = 60;
		this.ammoFromPickup = 10;
		this.bulletDamage = 45;
		this.fireRate = 1; //per second
		canFire = true;
	}

	public Bullet fireWeapon(int bulletX, int bulletY, String direction) {
		if(canFire) {
			currentAmmoCount = currentAmmoCount - 1;
			return new Bullet(ObjectId.Bullet, bulletX, bulletY, bulletDamage, false, direction);
		}
		else return null;
	}

	public void addAmmo() {
		currentAmmoCount += ammoFromPickup;
		if(currentAmmoCount > maxAmmoCount) { currentAmmoCount = maxAmmoCount; }
	}
}

