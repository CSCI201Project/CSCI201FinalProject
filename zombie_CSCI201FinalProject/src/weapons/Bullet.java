package weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import characters.Character;
import project2.GameObject;
import project2.ObjectId;

public class Bullet extends GameObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
			this.setVelX(speed);
			break;
		case "left":
			this.setVelX(-speed);
			break;
		case "up":
			this.setVelY(speed);
			break;
		case "down":
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
			if (isSlug){
				g.fillRect((int)x, (int)y, 2, 2);		
			}
			g.fillRect((int)x, (int)y, 5, 5);
		}
		
	}
	public void update(Vector<GameObject> objects) {
		x += velX;
		y += velY;
		if (Math.abs(x - originX) > 1000 || Math.abs(y - originY) > 1000){
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

