package project2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import AStarSearch.AStarTest;
import AStarSearch.SlickException;

public class AIChar extends Character implements Runnable{
	private AStarTest search;
	private PlayerChar target;
	public int startCol, startRow;
	public AIChar(double x, double y, ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.velX = 4;
		this.velY = 4;
		this.startCol = (int)this.x/PlayingField.tileWidth;
		this.startRow = (int)this.y/PlayingField.tileHeight;
		search = new AStarTest(this);
	}
	public void setMap(boolean[][] map){
		this.search.mapTiles = map;
		this.search.printMap();
	}
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int) y, 38, 38);
	}
	public void setTarget(PlayerChar player){
		this.target = player;
	}
	public void update(Vector<GameObject> objects) {
		/*try {
			//search.updatePath(this.startCol, this.startRow,(int) target.getX()/PlayingField.tileWidth, (int)target.getY()/PlayingField.tileHeight);
		} catch (SlickException e) {
            //System.out.println(e.getMessage());
         } */
	}
	public void moveAI(int nextCol, int nextRow){
		System.out.println("("+startCol+", "+startRow+") ---> "+"("+nextCol+", "+nextRow+")");
		int delay = 25;
		if(this.startCol != nextCol && this.startRow != nextRow){//Move diagonally
			//Move Down-Right
			boolean xDone = false, yDone = false;
			if((nextCol > this.startCol) && (nextRow > this.startRow)){ 
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
		else if(this.startCol != nextCol){
			//Move Right
			if(nextCol > this.startCol){
				while(this.x <= PlayingField.tileWidth*nextCol){
					
					this.x += velX;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{ //Move Left
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
		else if(this.startRow != nextRow){
			//Move Down
			if(nextRow > this.startRow){
				while(this.y <= PlayingField.tileHeight*nextRow){
					
					this.y += velY;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{ //Move Up
				while(this.y >= PlayingField.tileHeight*nextRow){
					
					this.y -= velY;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		this.startCol = nextCol;
		this.startRow = nextRow;
	}
	
	public void run() {
		while(true){
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
