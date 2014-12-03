package characters;

import imageProcessing.Animation;
import imageProcessing.Texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import project2.GameObject;
import project2.ObjectId;
import project2.PlayingField;
import AStarSearch.AStarTest;
import AStarSearch.SlickException;

public class AIChar extends Character implements Runnable{
	private AStarTest search;
	private PlayerChar target;
	public int startCol, startRow;
	private String facing = "right";
	private Texture texture;
	private Animation zombieWalk, zombieAttack;
	
	public AIChar(double x, double y, ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = 38;
		this.height = 38;
		this.velX = 2;
		this.velY = 2;
		this.startCol = (int)this.x/PlayingField.tileWidth;
		this.startRow = (int)this.y/PlayingField.tileHeight;
		search = new AStarTest(this);
		this.texture = new Texture();
		this.zombieWalk = new Animation(5, texture.zombieWalkSprites);
		this.zombieAttack = new Animation(5, texture.zombieAttackSprites);
	}
	public void setMap(boolean[][] map){
		this.search.mapTiles = map;
		//this.search.printMap();
	}
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		switch(facing) {
			case "right":
				g2d.rotate(0, x+19, y+18);
				break;
			case "left":
				g2d.rotate(Math.PI, x+19, y+18);
				break;
			case "up":
				g2d.rotate(3*Math.PI/2, x+19, y+18);
				break;
			case "down":
				g2d.rotate(Math.PI/2, x+19, y+18);
				break;
		}
		zombieWalk.drawAnimation(g2d, (int) x, (int) y);
		switch(facing) {
			case "right":
				g2d.rotate(0, x+19, y+18);
				break;
			case "left":
				g2d.rotate(-Math.PI, x+19, y+18);
				break;
			case "up":
				g2d.rotate(-3*Math.PI/2, x+19, y+18);
				break;
			case "down":
				g2d.rotate(-Math.PI/2, x+19, y+18);
				break;
		}
		
		
		g2d.dispose();
	}
	public void setTarget(PlayerChar player){
		this.target = player;
	}
	public void update(Vector<GameObject> objects) {
		zombieWalk.runAnimation();
	}
	public void moveAI(int nextCol, int nextRow){
		int delay = 25;
		if(this.startCol != nextCol && this.startRow != nextRow){//Move diagonally
			//Move Down-Right
			boolean xDone = false, yDone = false;
			if((nextCol > this.startCol) && (nextRow > this.startRow)){ 
				facing = "right";
				while(!xDone && !yDone){
					if(this.x <= PlayingField.tileWidth*nextCol){
						this.x += velX;
					}else{
						xDone = true;
					}
					if(this.y <= PlayingField.tileHeight*nextRow){
						this.y += velY;
					}else{
						yDone = true;
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}//Move Up-Left
			else if((nextCol < this.startCol) && (nextRow < this.startRow)){
				facing = "left";
				while(!xDone && !yDone){
					if(this.x >= PlayingField.tileWidth*nextCol){
						this.x -= velX;
					}else{
						xDone = true;
					}
					if(this.y >= PlayingField.tileHeight*nextRow){
						this.y -= velY;
					}else{
						yDone = true;
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}//Move Up-Right
			else if((nextCol > this.startCol) && (nextRow < this.startRow)){
				facing = "up";
				while(!xDone && !yDone){
					if(this.x <= PlayingField.tileWidth*nextCol){
						this.x += velX;
					}else{
						xDone = true;
					}
					if(this.y >= PlayingField.tileHeight*nextRow){
						this.y -= velY;
					}else{
						yDone = true;
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}//Move Down-Left
			else if((nextCol < this.startCol) && (nextRow > this.startRow)){
				facing = "left";
				while(!xDone && !yDone){
					if(this.x >= PlayingField.tileWidth*nextCol){
						this.x -= velX;
					}else{
						xDone = true;
					}
					if(this.y <= PlayingField.tileHeight*nextRow){
						this.y += velY;
					}else{
						yDone = true;
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else if(this.startCol != nextCol){//Move Horizontally
			//Move Right
			if(nextCol > this.startCol){
				facing = "right";
				while(this.x <= PlayingField.tileWidth*nextCol){
					
					this.x += velX;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{ //Move Left
				facing = "left";
				while(this.x >= PlayingField.tileWidth*nextCol){
					this.x -= velX;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else if(this.startRow != nextRow){//Move Vertically
			//Move Down
			if(nextRow > this.startRow){
				facing = "down";
				while(this.y <= PlayingField.tileHeight*nextRow){
					this.y += velY;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{ //Move Up
				facing = "up";
				while(this.y >= PlayingField.tileHeight*nextRow){
					this.y -= velY;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}//Update current location
		this.startCol = nextCol;
		this.startRow = nextRow;
	}
	
	public void run() {
		while(isOn){
			if (health <= 0){
				this.turnOff();
				break;
			}
			try {
					int targetCol, targetRow;
						targetCol = (int) (target.getX()+19)/PlayingField.tileWidth;
						targetRow = (int)(target.getY()+19)/PlayingField.tileHeight;
	
					if( startCol == targetCol && startRow ==targetRow){
						try {
							System.out.println("I am attacking you");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						search.updatePath(this.startCol, this.startRow, targetCol, targetRow);
						search.updateLocation();
						//TODO no such element exception
					}
			} catch (SlickException e) {
	            //System.out.println(e.getMessage());
	        }
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
