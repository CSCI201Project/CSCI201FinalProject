package project;

import java.awt.image.BufferedImage;

public class Texture {
	private SpriteSheet ss;
	private BufferedImage sheet;
	public BufferedImage[] sprites = new BufferedImage[3];
	
	public Texture(String filename) {
		try {
			BufferedImageLoader loader = new BufferedImageLoader();
			sheet = loader.loadImage(filename);
			ss = new SpriteSheet(sheet);
		} catch(Exception e) {
			e.printStackTrace();
		}
		getTextures();
	}
	
	private void getTextures() {
		sprites[0] = ss.grabImage(0, 0, 64, 38);
		sprites[1] = ss.grabImage(1, 0, 64, 38);
		sprites[2] = ss.grabImage(2, 0, 64, 38);
	}
}
