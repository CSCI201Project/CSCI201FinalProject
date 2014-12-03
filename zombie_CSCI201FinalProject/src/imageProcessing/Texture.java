package imageProcessing;


import java.awt.image.BufferedImage;

public class Texture {
	private SpriteSheet cs, is;
	private BufferedImage characterSheet, itemSheet;
	public BufferedImage[] survivorWalkSprites = new BufferedImage[3];
	public BufferedImage[] zombieWalkSprites = new BufferedImage[3];
	public BufferedImage[] zombieAttackSprites = new BufferedImage[6];
	public BufferedImage[] items = new BufferedImage[3];
	
	public Texture() {
		try {
			BufferedImageLoader loader = new BufferedImageLoader();
			characterSheet = loader.loadImage("images/oie_transparent.png");
			cs = new SpriteSheet(characterSheet);
			itemSheet = loader.loadImage("images/oie_transparent2.png");
			is = new SpriteSheet(itemSheet);
		} catch(Exception e) {
			e.printStackTrace();
		}
		getTextures();
	}
	
	private void getTextures() {
		survivorWalkSprites[0] = cs.grabImage(0, 0, 67, 40);
		survivorWalkSprites[1] = cs.grabImage(1, 0, 67, 40);
		survivorWalkSprites[2] = cs.grabImage(2, 0, 67, 40);
		
		zombieWalkSprites[0] = cs.grabImage(0, 3, 67, 40);
		zombieWalkSprites[1] = cs.grabImage(5, 3, 67, 40);
		zombieWalkSprites[2] = cs.grabImage(6, 3, 67, 40);
		
		zombieAttackSprites[0] = cs.grabImage(0, 3, 67, 40);
		zombieAttackSprites[1] = cs.grabImage(1, 3, 67, 40);
		zombieAttackSprites[2] = cs.grabImage(2, 3, 67, 40);
		zombieAttackSprites[3] = cs.grabImage(3, 3, 67, 40);
		zombieAttackSprites[4] = cs.grabImage(2, 3, 67, 40);
		zombieAttackSprites[5] = cs.grabImage(4, 3, 67, 40);
		
		items[0] = is.grabImage(0, 0, 46, 21);
		items[1] = is.grabImage(1, 0, 46, 21);
		items[2] = is.grabImage(2, 0, 46, 21);
	}
}
