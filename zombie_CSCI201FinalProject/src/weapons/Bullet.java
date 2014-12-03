package weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import characters.Character;
import project2.GameObject;
import project2.ObjectId;

public class Bullet extends GameObject implements Serializable {	
	private static final long serialVersionUID = 840682202075683150L;

	String direction;
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
		
		this.direction = direction;
		processDirection(direction);
	}
	private void processDirection(String direction){
		double speed = 7;
		switch(direction) {
		case "right":
			x += 38;
			y += 19;
			this.setVelX(speed);
			break;
		case "left":
			y += 13;
			this.setVelX(-speed);
			break;
		case "up":
			y += 32;
			x += 14;
			this.setVelY(speed);
			break;
		case "down":
			//y += 32;
			x += 20;
			this.setVelY(-speed);
			break;
		}
	}
	public int getDamage(){
		return damage;
	}
	public String getDirection() {
		return direction;
	}

	public void render(Graphics g) {
		if (onState()){
			g.setColor(Color.white);
			if(isSlug){
				g.setColor(Color.RED);
				g.fillRect((int)x, (int)y, 10, 10);		
			}
			g.fillRect((int)x, (int)y, 5, 5);
		}
	}
	
	public void update(Vector<GameObject> objects) {
		x += velX;
		y += velY;
		if(Math.abs(x - originX) > 1000 || Math.abs(y - originY) > 1000) {
			this.turnOff();
		}
		Collision(objects);
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 5, 5);
	}	
	
	private void Collision(Vector<GameObject> objects) {
		for(Iterator<GameObject> iterator = objects.iterator(); iterator.hasNext();) {
			GameObject object = iterator.next();
			if(object.getID() == ObjectId.HumanZombie || object.getID() == ObjectId.AlphaZombie ||
					object.getID() == ObjectId.NormalZombie) {
				if(getBounds().intersects(object.getBounds()) && onState()) {
					//object takes damage
					Character c = (Character) object;
					c.takeDamage(damage);
					//Bullet turns off.
					this.setX(object.getX());
					this.setY(object.getY());
					this.setVelX(0);
					this.setVelY(0);
					this.turnOff();
					System.out.println("Bullet hit");
				}
			}
		}
	}
}

