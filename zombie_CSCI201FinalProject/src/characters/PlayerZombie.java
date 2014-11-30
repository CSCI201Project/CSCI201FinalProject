package characters;


public class PlayerZombie extends PlayerMovement{
	public PlayerZombie(PlayerChar player) {
		super(player);
	}

	public void attack() {
		player.attacking = true;
	}
}
