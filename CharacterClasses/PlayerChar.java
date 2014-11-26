package project2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

public class PlayerChar extends Character{
	private String playerName;
	private Texture texture;
	private Animation playerWalk, zombieWalk, zombieAttack;
	private PlayerMovement pm;
	private String facing = "right";
	protected boolean aiming = false;
	protected boolean attacking = false;
	
	public PlayerChar(String name, ObjectId id) {
		super(id);
		this.playerName = name;
		this.setPlayerType();
		this.texture = new Texture();
		this.playerWalk = new Animation(5, texture.survivorWalkSprites);
		this.zombieWalk = new Animation(5, texture.zombieWalkSprites);
		this.zombieAttack = new Animation(5, texture.zombieAttackSprites);
		this.x = 200;
		this.y = 150;
		this.width = 38;
		this.height = 38;
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
			//playerWalk.drawAnimation(g, (int) x, (int) y);
			zombieWalk.drawAnimation(g, (int) x, (int) y);
		}
		else {
			g2d.drawImage(texture.zombieWalkSprites[0], (int) x, (int) y, null);
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

	public void update(Vector<GameObject> objects) {
		this.x += this.velX;
		this.y += this.velY;
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
		//playerWalk.runAnimation();
		zombieWalk.runAnimation();
		Collision(objects);
	}
	
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
			if(object.id == ObjectId.NormalZombie) {
				AIChar block = (AIChar) object;
				if(getBoundsLeft().intersects(block.getBounds())) {
					velX = 0;
					x = block.x + block.width;
				}
				if(getBoundsRight().intersects(block.getBounds())) {
					velX = 0;
					x = block.x - width;
				}
				if(getBoundsTop().intersects(block.getBounds())) {
					velY = 0;
					y = block.y + block.height;
				}
				if(getBoundsBottom().intersects(block.getBounds())) {
					velY = 0;
					y = block.y - height;
				}
			}
		}
	}
}

