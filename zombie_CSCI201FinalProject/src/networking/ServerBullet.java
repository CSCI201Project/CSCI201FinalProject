package networking;

import java.io.Serializable;

public class ServerBullet implements Serializable {
	private static final long serialVersionUID = 2L;

	public double x, y;
	
	private int damage;
	private String direction;
	
	public ServerBullet(double x, double y, int damage, String direction) {
		this.x = x;
		this.y = y;
		
		this.damage = damage;
		this.direction = direction;
	}
	
	public int getDamage() {
		return this.damage;
	}
	public String getDirection() {
		return this.direction;
	}
}