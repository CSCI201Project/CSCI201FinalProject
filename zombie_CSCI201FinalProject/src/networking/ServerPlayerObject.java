package networking;

import java.io.Serializable;

public class ServerPlayerObject implements Serializable {
	private static final long serialVersionUID = 3L;
	
	private double x, y;
	private double velx, vely;
	private String name;
	private boolean init;
	
	public ServerPlayerObject(double x, double y, String name) {
		this(x, y, name, false);
	}
	public ServerPlayerObject(double x, double y, String name, boolean init) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.init = init;
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	
	public double getVelX() { return this.velx; }
	public double getVelY() { return this.vely; }
	public void setVelX(double velx) { this.velx = velx; }
	public void setVelY(double vely) { this.vely = vely; }
	
	public String getName() {
		return this.name;
	}
	public boolean getInit() {
		return this.init;
	}
}