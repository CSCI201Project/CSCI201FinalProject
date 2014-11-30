package weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Vector;

import characters.Character;
import project2.GameObject;
import project2.ObjectId;

public class Bullet extends GameObject{
	int direction;
	int damage;
	boolean isSlug;
	int originX, originY;
	
	public Bullet(ObjectId id, int originX, int originY, int damage, boolean slug,
			String direction) {
		super(id);
		x = originX;
		y = originY;
		this.originX = originX;
		this.originY = originY;
		this.damage = damage;
		isSlug = slug;
		processDirection(direction);
	}
	private void processDirection(String direction){
		switch(direction) {
		case "right":
			this.setVelX(0.5);
			break;
		case "left":
			this.setVelX(-0.5);
			break;
		case "up":
			this.setVelY(0.5);
			break;
		case "down":
			this.setVelY(-0.5);
			break;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		if (isSlug){
			g.fillRect((int)x, (int)y, 2, 2);		
		}
		g.fillRect((int)x, (int)y, 3, 3);
		// TODO Auto-generated method stub
		
	}
	public void update(Vector<GameObject> objects) {
		// TODO Auto-generated method stub
		this.x += this.velX;
		this.y += this.velY;
		if (Math.abs(x - originX) > 100 || Math.abs(y - originY) > 100){
			this.turnOff();
		}
		Collision(objects);
	}
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x, (int)y, 2, 2);
	}
	
	
	private void Collision(Vector<GameObject> objects) {
		for(Iterator<GameObject> iterator = objects.iterator(); iterator.hasNext();) {
			GameObject object = iterator.next();
			if(object.getID() == ObjectId.HumanZombie || object.getID() == ObjectId.AlphaZombie ||
					object.getID() == ObjectId.NormalZombie) {
				if(getBounds().intersects(object.getBounds())) {
					//object takes damage
					Character c = (Character) object;
					c.takeDamage(damage);
					//Bullet turns off.
					this.turnOff();
				}
			}
		}
	}
}

