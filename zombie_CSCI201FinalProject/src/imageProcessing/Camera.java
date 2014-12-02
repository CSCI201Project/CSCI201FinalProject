package imageProcessing;

import project2.PlayingField;
import characters.PlayerChar;

public class Camera {
	private double x,y;
	
	public Camera(double x, double y){
		this.x = x;
		this.y = y;
	}
	public void update(PlayerChar player){
		this.x = -player.getX() + PlayingField.windowWidth/2 - player.getWidth();
		this.y = -player.getY() + PlayingField.windowHeight/2 - player.getHeight();
		
		
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
}
