package characters;

import imageProcessing.Animation;
import imageProcessing.Texture;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;

import project2.GameObject;
import project2.GameObjectHandler;
import project2.ObjectId;
import project2.PlayingField;
import weapons.AssaultRifle;
import weapons.Bullet;
import weapons.Pistol;
import weapons.Shotgun;
import weapons.Weapon;

public class PlayerChar extends Character{
	private String playerName;
	private Texture texture;
	private BufferedImage survivorIdle;
	private Animation survivorWalkPistol, survivorWalkRifle, survivorWalkShotgun, survivorWalk;
	private Animation zombieWalk, zombieAttack;
	private PlayerMovement pm;
	private String facing = "right";
	protected boolean aiming = false;
	protected boolean attacking = false;
	private boolean hit = false;
	private boolean appear = true;
	private Vector<Weapon> guns;
	private Weapon currentGun;
	private GameObjectHandler handler;
	private int count = 0;
	
	public PlayerChar(String name, double x, double y, ObjectId id) {
		super(id);
		this.playerName = name;
		this.setPlayerType();
		this.texture = new Texture();
		this.survivorWalkPistol = new Animation(5, texture.survivorWalkPistolSprites);
		this.survivorWalkRifle = new Animation(5, texture.survivorWalkRifleSprites);
		this.survivorWalkShotgun = new Animation(5, texture.survivorWalkShotgunSprites);
		this.zombieWalk = new Animation(5, texture.zombieWalkSprites);
		this.zombieAttack = new Animation(5, texture.zombieAttackSprites);
		this.x = x;
		this.y = y;
		this.width = 38;
		this.height = 38;
		
		guns = new Vector<Weapon>();
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

	private void displayHud(Graphics g){
		//HUD Information
		g.setFont(new Font("Arial",Font.BOLD, 18));
		g.setColor(Color.WHITE);
		g.drawString("Time Left:",(int) this.x, (int) this.y - 220);
				
		g.setFont(new Font("Arial",Font.BOLD, 14));
		g.drawString("Survivors Left: 3",(int) this.x - 290,(int) this.y -225);
				
		g.setFont(new Font("Arial",Font.BOLD, 18));
		if(this.getHealth() > 80)
			g.setColor(Color.GREEN);
		else if(this.getHealth() > 20)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);
		g.setFont(new Font("Arial",Font.BOLD, 14));
		g.drawString("Health: "+this.getHealth(),(int) this.x +290,(int) this.y - 225);
		
		if(this.id == ObjectId.HumanSurvivor) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.BOLD, 14));
			g.drawString("Current Weapon",(int) this.x + 250,(int) this.y + 260);
			g.drawString(this.currentGun.getAmmo()+"/"+this.currentGun.getMaxAmmo(),(int) this.x + 290,(int) this.y + 310);
					
			ImageIcon icon;
			if(this.currentGun.getId() == ObjectId.Rifle){
				icon = new ImageIcon("images/rifle_sprite.png","gun");
				Image image = icon.getImage();
				g.drawImage(image,(int) this.x + 265,(int) this.y + 265, null);
			}
			else if(this.currentGun.getId() == ObjectId.Shotgun){
				icon = new ImageIcon("images/shotgun_sprite.png","gun");
				Image image = icon.getImage();
				g.drawImage(image,(int) this.x + 265,(int) this.y + 270, null);
			}
			else{
				icon = new ImageIcon("images/pistol_sprite.png","gun");
				Image image = icon.getImage();
				g.drawImage(image,(int) this.x + 285,(int) this.y + 267, null);
			}
		}		
	}
	
	public void render(Graphics g) {
		this.render(g, true);
	}
	public void render(Graphics g, boolean renderHUD) {
		if(renderHUD)
			displayHud(g);

//        AffineTransform at = new AffineTransform();
//        at.setToRotation(getAngle(), x + (rectangle.width / 2), y + (rectangle.height / 2));
//        at.translate(x, y);
//        g2d.setTransform(at);
//        g2d.draw(rectangle);
//        g2d.dispose();
		
		if(appear) {
			Graphics2D g2d = (Graphics2D) g.create();
//			Graphics2D g2d = (Graphics2D) g;
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
					survivorWalk.drawAnimation(g2d, (int) x, (int) y);
				else if(this.id == ObjectId.HumanZombie)
					zombieWalk.drawAnimation(g2d, (int) x, (int) y);
			}
			else {
				if(this.id == ObjectId.HumanSurvivor)
					g2d.drawImage(survivorIdle, (int) x, (int) y, null);
				else if(this.id == ObjectId.HumanZombie)
					g2d.drawImage(texture.zombieWalkSprites[0], (int) x, (int) y, null);
			}
			if(attacking) {
				zombieAttack.drawAnimation(g2d, (int) x, (int) y);
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
			
			g2d.dispose();
		}
	}
	public void pickUpAmmo(){
		for (Weapon gun : guns){
			gun.addAmmo();
		}
	}
	public boolean pickUpWeapon(ObjectId id){
		boolean alreadyHas = false;
		for (Weapon gun : guns){
			if (gun.getId() == id){
				alreadyHas = true;
			}
		}
		if (!alreadyHas){
			if (id == ObjectId.Shotgun){
				guns.add(new Shotgun(ObjectId.Shotgun));
				return true;
			}
			else if (id == ObjectId.Rifle){
				guns.add(new AssaultRifle(ObjectId.Rifle));
				return true;
			}
		}
		return false;
	}
	public void cycleWeapon(){
		int i = guns.indexOf(currentGun);
		i++; i = i % guns.size();
		currentGun = guns.get(i);
	}
	public void fireWeapon(){
		if (currentGun.outOfAmmo() || currentGun.safetyOn()){return;}
		Bullet b = currentGun.fireWeapon((int)x, (int)y, facing);
		handler.addBullets(b);
	}
	public void attack() {
		
	}
	public void update(Vector<GameObject> objects) {
		this.x += this.velX;
		this.y += this.velY;
		
		updateStates(objects);

		Collision(objects);
	}
	public void updateStates(Vector<GameObject> objects) {
		if (health <= 0){
			if(this.id != ObjectId.HumanZombie){
				PlayingField pf = this.handler.getPlayingField();
				PlayingField.removeKeyListener(pf, this);
				
				this.setID(ObjectId.HumanZombie);
				this.setPlayerType();
				this.health = 100;
				
				PlayingField.addKeyListener(pf, this);
			}
		}
		if(currentGun instanceof Pistol) {
			survivorIdle = texture.survivorWalkPistolSprites[0];
			survivorWalk = survivorWalkPistol;
		}
		if(currentGun instanceof AssaultRifle) {
			survivorIdle = texture.survivorWalkRifleSprites[0];
			survivorWalk = survivorWalkRifle;
		}			
		if(currentGun instanceof Shotgun) {
			survivorIdle = texture.survivorWalkShotgunSprites[0];
			survivorWalk = survivorWalkShotgun;
		}
		
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
		if(hit) {
			++count;
			if(appear) {
				appear = false;
			}
			else {
				appear = true;
			}
			if(count == 30) {
				count = 0;
				hit = false;
				appear = true;
			}
		}
		if(this.id == ObjectId.HumanSurvivor)
			survivorWalk.runAnimation();
		else if(this.id == ObjectId.HumanZombie)
			zombieWalk.runAnimation();
	}
	public void takeDamage(GameObject object) {
		hit = true;
		if(isSurvivor()) {
			health -= 10;
		}
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public int getCurrentAmmoCount() {
		return currentGun.currentAmmoCount;
	}
	public Rectangle getBounds() { return new Rectangle((int)x, (int)y, width, height); }
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
	public boolean isHit() {
		return hit;
	}
	
	public void setAiming(boolean aiming) {
		this.aiming = aiming;
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
			else if((this.getID() == ObjectId.HumanSurvivor) && (object.getID() == ObjectId.NormalZombie || object.getID() == ObjectId.AlphaZombie)) {
				if(getBoundsLeft().intersects(object.getBounds())) {
					if(!hit) {
						takeDamage(object);
					}
					velX = 0;
					x = object.getX() + object.getWidth();
				}
				if(getBoundsRight().intersects(object.getBounds())) {
					if(!hit) {
						takeDamage(object);
					}
					velX = 0;
					x = object.getX() - width;
				}
				if(getBoundsTop().intersects(object.getBounds())) {
					if(!hit) {
						takeDamage(object);
					}
					velY = 0;
					y = object.getY() + object.getHeight();
				}
				if(getBoundsBottom().intersects(object.getBounds())) {
					if(!hit) {
						takeDamage(object);
					}
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
					if(pickUpWeapon(object.getID()))
						object.turnOff();
				}
			}
		}
	}
}

