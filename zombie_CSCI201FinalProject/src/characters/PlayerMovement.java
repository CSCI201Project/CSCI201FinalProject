package characters;

import gui.SoundPlayer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import project2.PlayingField;

public abstract class PlayerMovement extends KeyAdapter{
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
			this.player.setAiming(true);
		else if(key == KeyEvent.VK_Q)
			this.player.cycleWeapon();
		else if(key == KeyEvent.VK_SPACE)
		{	
			if(this.player.getCurrentAmmoCount() != 0){
				SoundPlayer sp = new SoundPlayer("shotgunsound.wav");
				sp.playSoundOnce();
			} else {
				SoundPlayer sp = new SoundPlayer("gunclick.wav");
				sp.playSoundOnce();
			}
			//sp.stopSound();
			attack();
		}
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
			this.player.setAiming(false);
		for(AIChar s: PlayingField.AIList){
			if(key != KeyEvent.VK_SPACE){
				double distance = Math.sqrt(Math.pow(player.getX()-s.getX(), 2)+Math.pow(player.getY()-s.getY(), 2));
				System.out.println("Distance: " + distance);
				if(distance<100){
					System.out.println("Started a new SP");
					SoundPlayer sp = new SoundPlayer("zombie.wav");
					sp.playSoundOnce();
					break;
				}
			}
		}
	}
}
