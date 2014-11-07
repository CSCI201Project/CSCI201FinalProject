package project;

public abstract class GameObject {
	protected double x, y;
	protected double velX, velY;
	
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
