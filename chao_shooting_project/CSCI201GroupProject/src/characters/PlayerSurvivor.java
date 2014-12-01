package characters;


public class PlayerSurvivor extends PlayerMovement{
	
	public PlayerSurvivor(PlayerChar player) {
		super(player);
	}

	public void attack() {
		player.fireWeapon();
	}

}
