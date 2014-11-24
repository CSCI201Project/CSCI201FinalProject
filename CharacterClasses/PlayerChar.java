package project2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

public class PlayerChar extends Character{
	private String playerName;
	private Texture playerTexture;
	private Animation playerWalk;
	private PlayerMovement pm;
	private String facing = "right";
	
<<<<<<< HEAD
	public PlayerChar(String name) {
		this.playerName = name;
		this.playerTexture = new Texture("images/oie_transparent.png");
		this.playerWalk = new Animation(5, playerTexture.sprites);
		this.x = 20;
		this.y = 15;
=======
	public PlayerChar(String name, ObjectId id) {
		super(id);
		this.playerName = name;
		this.setPlayerType();
		this.playerTexture = new Texture("images/oie_transparent.png");
		this.playerWalk = new Animation(5, playerTexture.sprites);
		this.x = 0;
		this.y = 0;
>>>>>>> 48a95a37cdac62f4478f140347d260d44e914ce4
	}
	
	public PlayerMovement getKeyAdapter(){
		return pm;
	}
	
<<<<<<< HEAD
	public void setPlayerType(int num){
		if(num == SURVIVOR){
=======
	public void setPlayerType(){
		if(this.id == ObjectId.HumanSurvivor){
>>>>>>> 48a95a37cdac62f4478f140347d260d44e914ce4
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
			g2d.rotate(0, x+32, y+18);
			break;
		case "left":
			g2d.rotate(Math.PI, x+32, y+18);
			break;
		case "up":
			g2d.rotate(Math.PI/2, x+32, y+18);
			break;
		case "down":
			g2d.rotate(3*Math.PI/2, x+32, y+18);
			break;
		}
<<<<<<< HEAD
=======
	
>>>>>>> 48a95a37cdac62f4478f140347d260d44e914ce4
		if(velX != 0 || velY != 0) {
			playerWalk.drawAnimation(g, (int) x, (int) y);
		}
		else {
<<<<<<< HEAD
			g.drawImage(playerTexture.sprites[0], (int) x, (int) y, null);
=======
			g.drawImage(playerTexture.sprites[0], (int) this.x, (int) this.y, null);
>>>>>>> 48a95a37cdac62f4478f140347d260d44e914ce4
		}
	}

	public void update(Vector<GameObject> objects) {
		this.x += this.velX;
		this.y += this.velY;
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
		playerWalk.runAnimation();
	}
}

