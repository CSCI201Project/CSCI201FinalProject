package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class PlayerChar extends Character{
	private String playerName;
	
	private PlayerMovement pm;
	
	public PlayerChar(String name){
		this.playerName = name;
		this.x = 20;
		this.y = 15;
	}
	public PlayerMovement getKeyAdapter(){
		return pm;
	}
	public void setPlayerType(int num){
		if(num == SURVIVOR){
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
		g.setColor(Color.BLUE);
		g.fillRect((int)this.x,(int) this.y, this.width, this.height);
		
	}

	public void update(Vector<GameObject> objects) {
		this.x+=this.velX;
		this.y+=this.velY;
	}
}

