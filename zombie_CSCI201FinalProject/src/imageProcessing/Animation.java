package imageProcessing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	public boolean cycleComplete = false;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage[] args) {
		this.speed = speed;
		this.frames = args.length;
		images = new BufferedImage[args.length];
		for(int i=0; i<args.length; ++i) {
			images[i] = args[i];
		}
	}
	
	public void runAnimation() {
		++index;
		if(index > speed) {
			index = 0;
			nextFrame();
		}
	}
	
	public void drawAnimation(Graphics g, int x, int y) {
		g.drawImage(currentImg, x, y, null);
	}
	
	private void nextFrame() {
		for(int i=0; i<frames; ++i) {
			if(count == i) {
				currentImg = images[i];
			}
		}
		++count;
		if(count > frames) {
			count = 0;
			cycleComplete = true;
		}
	}
}
