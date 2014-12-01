package weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import project2.GameObject;
import project2.ObjectId;

public class Pistol extends Weapon{
	
	public Pistol(ObjectId id) {
		super(id);
		this.currentAmmoCount = 30;
		this.maxAmmoCount = 60;
		this.ammoFromPickup = 10;
		this.bulletDamage = 45;
		this.fireRate = 1; //per second
	}

	public Bullet fireWeapon(int bulletX, int bulletY, String direction) {
		// TODO Auto-generated method stub
		currentAmmoCount = currentAmmoCount - 1;
		return new Bullet(ObjectId.Bullet, bulletX, bulletY, bulletDamage, false, direction);

	}

	public void addAmmo() {
		// TODO Auto-generated method stub
		currentAmmoCount += ammoFromPickup;
		 
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public void update(Vector<GameObject> objects) {
		// TODO Auto-generated method stub
		
	}

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}

