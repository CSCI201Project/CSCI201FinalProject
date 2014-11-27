package project2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

abstract class PlayerMovement extends KeyAdapter{
	protected PlayerChar player;
	
	public abstract void attack();
	public PlayerMovement(PlayerChar player){
		this.player = player;
	}
	
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode();

		if(key == KeyEvent.VK_W){
			this.player.setVelY(-5);
		}
		else if(key == KeyEvent.VK_A)
			this.player.setVelX(-5);
		else if(key == KeyEvent.VK_S)
			this.player.setVelY(5);
		else if(key == KeyEvent.VK_D)
			this.player.setVelX(5);
		else if(key == KeyEvent.VK_J)
			this.player.aiming = true;
		else if(key == KeyEvent.VK_SPACE)
			attack();
	}
	
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		
		if(key == KeyEvent.VK_W)
			this.player.setVelY(0);
		else if(key == KeyEvent.VK_A)
			this.player.setVelX(0);
		else if(key == KeyEvent.VK_S)
			this.player.setVelY(0);
		else if(key == KeyEvent.VK_D)
			this.player.setVelX(0);
		else if(key == KeyEvent.VK_J)
			this.player.aiming = false;
	}
}
