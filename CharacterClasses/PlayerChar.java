package project;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerChar extends Character{
	private String playerName;
	public static final int ZOMBIE = 0;
	public static final int SURVIVOR = 1;
	private PlayerMovement pm;
	
	public PlayerChar(String name){
		this.playerName = name;
		
	}
	
	public void setPlayerType(int num){
		if(num == SURVIVOR)
			pm = new PlayerSurvivor(this);
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
}

