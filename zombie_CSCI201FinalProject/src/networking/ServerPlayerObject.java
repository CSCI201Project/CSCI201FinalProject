package networking;

import java.io.Serializable;

import project2.ObjectId;

public class ServerPlayerObject implements Serializable {
	private static final long serialVersionUID = 3L;
	
	private double x, y;
	private double velx, vely;
	private String name;
	private boolean init;
	private ObjectId id;
	
	public ServerPlayerObject(double x, double y, String name, ObjectId id) {
		this(x, y, name, id, false);
	}
	public ServerPlayerObject(double x, double y, String name, ObjectId id, boolean init) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.init = init;
		this.id = id;
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	
	public double getVelX() { return this.velx; }
	public double getVelY() { return this.vely; }
	public void setVelX(double velx) { this.velx = velx; }
	public void setVelY(double vely) { this.vely = vely; }
	
	public ObjectId getID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public boolean getInit() {
		return this.init;
	}
}