package weapons;

import project2.ObjectId;

public class AssaultRifle extends Weapon {
	public AssaultRifle(ObjectId id) {
		super(id);
		this.currentAmmoCount = 30;
		this.maxAmmoCount = 90;
		this.ammoFromPickup = 15;
		this.bulletDamage = 60;
		this.fireRate = 2; //per second
		canFire = true;
	}

	public Bullet fireWeapon(int bulletX, int bulletY, String direction) {
		currentAmmoCount = currentAmmoCount - 1;
		return new Bullet(ObjectId.Bullet, bulletX, bulletY, bulletDamage, false, direction);
	}

	public void addAmmo() {
		currentAmmoCount += ammoFromPickup;
		if(currentAmmoCount > maxAmmoCount) { currentAmmoCount = maxAmmoCount; }
	}
}
