package networking;

import java.io.Serializable;

public class ServerZombieObject implements Serializable {
	private static final long serialVersionUID = 4L;

	private double x, y;
	private double velx, vely;
	private int health;
	private int i;
	
	public ServerZombieObject(double x, double y, int hp, int i) {
		this.x = x;
		this.y = y;
		this.health = hp;
		this.i = i;
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	
	public double getVelX() { return this.velx; }
	public double getVelY() { return this.vely; }
	public void setVelX(double velx) { this.velx = velx; }
	public void setVelY(double vely) { this.vely = vely; }
	
	public int getHealth() {
		return this.health;
	}
	
	public int getIterator() {
		return this.i;
	}
}