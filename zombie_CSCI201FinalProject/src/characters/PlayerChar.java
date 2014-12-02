package characters;

import imageProcessing.Animation;
import imageProcessing.Texture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import project2.GameObject;
import project2.GameObjectHandler;
import project2.ObjectId;
import weapons.AssaultRifle;
import weapons.Bullet;
import weapons.Pistol;
import weapons.Shotgun;
import weapons.Weapon;

public class PlayerChar extends Character{
	private String playerName;
	private Texture texture;
	private Animation playerWalk, zombieWalk, zombieAttack;
	private PlayerMovement pm;
	private String facing = "right";
	protected boolean aiming = false;
	protected boolean attacking = false;
	private Vector<Weapon> guns = new Vector<Weapon>();
	private Weapon currentGun;
	private GameObjectHandler handler;
	
	public PlayerChar(String name, double x, double y, ObjectId id) {
		super(id);
		this.playerName = name;
		this.setPlayerType();
		this.texture = new Texture();
		this.playerWalk = new Animation(5, texture.survivorWalkSprites);
		this.zombieWalk = new Animation(5, texture.zombieWalkSprites);
		this.zombieAttack = new Animation(5, texture.zombieAttackSprites);
		this.x = x;
		this.y = y;
		this.width = 38;
		this.height = 38;
		guns.add(new Pistol(ObjectId.Pistol));
		currentGun = guns.firstElement();
	}
	
	public void setHandler(GameObjectHandler handler){
		this.handler = handler;
	}
	public PlayerMovement getKeyAdapter(){
		return pm;
	}
	
	public void setPlayerType(){
		if(this.id == ObjectId.HumanSurvivor){
			pm = new PlayerSurvivor(this);
		}
		else
			pm = new PlayerZombie(this);
	}
	
	public boolean isSurvivor(){
		if(pm == null)
			return false;
		else if(pm instanceof PlayerSurvivor){
			return true;
		}
		else
			return false;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		switch(facing) {
		case "right":
			g2d.rotate(0, x+19, y+18);
			break;
		case "left":
			g2d.rotate(Math.PI, x+19, y+18);
			break;
		case "up":
			g2d.rotate(Math.PI/2, x+19, y+18);
			break;
		case "down":
			g2d.rotate(3*Math.PI/2, x+19, y+18);
			break;
		}
		if(velX != 0 || velY != 0) {
			if(this.id == ObjectId.HumanSurvivor)
				playerWalk.drawAnimation(g, (int) x, (int) y);
			else if(this.id == ObjectId.HumanZombie)
				zombieWalk.drawAnimation(g, (int) x, (int) y);
		}
		else {
			if(this.id == ObjectId.HumanSurvivor)
				g.drawImage(texture.survivorWalkSprites[0], (int) x, (int) y, null);
			else if(this.id == ObjectId.HumanZombie)
				g.drawImage(texture.zombieWalkSprites[0], (int) x, (int) y, null);
		}
		if(attacking) {
			zombieAttack.drawAnimation(g, (int) x, (int) y);
		}
		switch(facing) {
		case "right":
			g2d.rotate(0, x+19, y+18);
			break;
		case "left":
			g2d.rotate(-Math.PI, x+19, y+18);
			break;
		case "up":
			g2d.rotate(-Math.PI/2, x+19, y+18);
			break;
		case "down":
			g2d.rotate(-3*Math.PI/2, x+19, y+18);
			break;
		}
	}
	public void pickUpAmmo(){
		for (Weapon gun : guns){
			gun.addAmmo();
		}
	}
	public void pickUpWeapon(ObjectId id){
		boolean alreadyHas = false;
		for (Weapon gun : guns){
			if (gun.getId() == id){
				alreadyHas = true;
			}
		}
		if (!alreadyHas){
			if (id == ObjectId.Shotgun){
				guns.add(new Shotgun(ObjectId.Shotgun));
			}
			else if (id == ObjectId.Rifle){
				guns.add(new AssaultRifle(ObjectId.Rifle));
			}
		}
		
	}
	public void cycleWeapon(){
		int i = guns.indexOf(currentGun);
		i++; i = i % guns.size();
		currentGun = guns.get(i);
		
	}
	public void fireWeapon(){
		if (currentGun.outOfAmmo()){return;}
		Bullet b = currentGun.fireWeapon((int)x, (int)y, facing);
		handler.addBullets(b);
	}
	public void update(Vector<GameObject> objects) {
		this.x += this.velX;
		this.y += this.velY;
		
		updateStates(objects);
		
		Collision(objects);
	}
	public void updateStates(Vector<GameObject> objects) {
		if(!aiming) {
			if(velX > 0) {
				facing = "right";
			}
			else if(velX < 0) {
				facing = "left";
			}
			else if(velY > 0) {
				facing = "up";
			}
			else if(velY < 0) {
				facing = "down";
			}
		}
		if(attacking) {
			zombieAttack.runAnimation();
			if(zombieAttack.cycleComplete) {
				attacking = false;
				zombieAttack.cycleComplete = false;
			}
		}
		if(this.id == ObjectId.HumanSurvivor)
			playerWalk.runAnimation();
		else if(this.id == ObjectId.HumanZombie)
			zombieWalk.runAnimation();
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public Rectangle getBounds() { return null; }
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y + 5, 5, height - 10);
	}
	public Rectangle getBoundsRight() {
		return new Rectangle((int)x + (width-5), (int)y + 5, 5, height - 10);
	}
	public Rectangle getBoundsTop() {
		return new Rectangle((int)x + 5, (int)y, width-10, height/2);
	}
	public Rectangle getBoundsBottom() {
		return new Rectangle((int)x + 5, (int)y + (height/2), width-10, height/2);
	}
	
	private void Collision(Vector<GameObject> objects) {
		for(GameObject object : objects) {
			if(object.getID() == ObjectId.Tree || object.getID() == ObjectId.Bush ||
					object.getID() == ObjectId.Tombstone || object.getID() == ObjectId.Cross) {
				if(getBoundsLeft().intersects(object.getBounds())) {
					velX = 0;
					x = object.getX() + object.getWidth();
				}
				if(getBoundsRight().intersects(object.getBounds())) {
					velX = 0;
					x = object.getX() - width;
				}
				if(getBoundsTop().intersects(object.getBounds())) {
					velY = 0;
					y = object.getY() + object.getHeight();
				}
				if(getBoundsBottom().intersects(object.getBounds())) {
					velY = 0;
					y = object.getY() - height;
				}
			}
			else if (object.getID() == ObjectId.Ammo && this.isSurvivor()){
				if(getBounds().intersects(object.getBounds())) {
					pickUpAmmo();
					object.turnOff();
				}
			}
			else if ((object.getID() == ObjectId.Rifle && this.isSurvivor()) ||
					(object.getID() == ObjectId.Shotgun && this.isSurvivor())){
				if(getBounds().intersects(object.getBounds())){
					pickUpWeapon(object.getID());
				}
			}
		}
	}
}

