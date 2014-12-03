package weapons;

import project2.ObjectId;

public class Shotgun extends Weapon{

	public Shotgun(ObjectId id) {
		super(id);
		this.currentAmmoCount = 5;
		this.maxAmmoCount = 15;
		this.ammoFromPickup = 5;
		this.bulletDamage = 100;
		this.fireRate = 0.5; //per second
		canFire = true;
	}

	public Bullet fireWeapon(int bulletX, int bulletY, String direction) {
		if(canFire) {
			currentAmmoCount = currentAmmoCount -1;
			return new Bullet(ObjectId.Bullet, bulletX, bulletY, bulletDamage, true, direction);
		}
		else return null;
	}

	public void addAmmo() {
		currentAmmoCount += ammoFromPickup;
		if(currentAmmoCount > maxAmmoCount) { currentAmmoCount = maxAmmoCount; }
	}
}
