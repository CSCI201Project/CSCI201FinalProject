package project2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public abstract class GameObject {
	protected double x = 0, y = 0;
	protected int width, height;
	protected ObjectId id;
	protected double velX = 0, velY = 0;
	protected boolean isOn;
	
	public GameObject(ObjectId id){
		this.id = id;
		isOn = true;
	}
	//All children must implement their physical image and updates(location, velocity, etc.)
	public abstract void render(Graphics g);
	public abstract void update(Vector<GameObject> objects);
	public abstract Rectangle getBounds();
	public void turnOff(){
		isOn = false;
	}
	public boolean onState(){
		return isOn;
	}
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
	public ObjectId getID(){
		return this.id;
	}
	public void setID(ObjectId id){
		this.id = id;
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
