package project;

import java.awt.Graphics;
import java.util.Vector;

public abstract class GameObject {
	protected double x, y;
	protected double velX = 0, velY = 0;
	public static final int ZOMBIE = 0;
	public static final int SURVIVOR = 1;
	public static final int WEAPON = 2;
	public static final int OTHER = 3;
	
	public abstract void render(Graphics g);
	public abstract void update(Vector<GameObject> objects);
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public void setVelX(double x){
		this.velX = x;
	}
	public void setVelY(double y){
		this.velY = y;
	}
	public double getVelX(){
		return velX;
	}
	public double getVelY(){
		return velY;
	}
}
