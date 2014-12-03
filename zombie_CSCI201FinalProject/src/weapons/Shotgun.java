package weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import project2.GameObject;
import project2.ObjectId;

public class Shotgun extends Weapon{

	public Shotgun(ObjectId id) {
		super(id);
		this.currentAmmoCount = 5;
		this.maxAmmoCount = 15;
		this.ammoFromPickup = 5;
		this.bulletDamage = 80;
		this.fireRate = 0.5; //per second
	}

	public Bullet fireWeapon(int bulletX, int bulletY, String direction) {
		// TODO Auto-generated method stub
		currentAmmoCount = currentAmmoCount -1;
		return new Bullet(ObjectId.Bullet, bulletX, bulletY, bulletDamage, false, direction);

	}

	public void addAmmo() {
		// TODO Auto-generated method stub
		
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
