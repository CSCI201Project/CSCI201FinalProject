package project2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;

import AStarSearch.AStarTest;

class PlayingField extends Canvas implements Runnable{
	static public int windowWidth;
	static public int windowHeight;
	private GameObjectHandler gameObjects;
	private BufferedImage level = null;
	private AStarTest mainMap;
	private Camera cam;
	static protected int tileHeight = 50;
	static protected int tileWidth = 50;
	
	public PlayingField(){
		this.setBackground(Color.BLACK);
		gameObjects = new GameObjectHandler(tileWidth,tileHeight);
		this.mainMap = new AStarTest(new AIChar(0,0,ObjectId.NormalZombie)); //This AI is not actually used
		
		for(int i = 0; i<mainMap.width; i++){
			for(int j = 0; j<mainMap.height;j++){
				mainMap.mapTiles[i][j] = true;
			}
		}
	}
	private void init(){
		windowWidth = this.getWidth();
		windowHeight = this.getHeight();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("res/map.png"); //Loading the map
		LoadImageMap(level);
		gameObjects.init(mainMap.mapTiles);
		this.cam = new Camera(0,0);
		this.addKeyListener(gameObjects.getController());
		gameObjects.startAI();
	}

	public void run() {
		this.init();
		
		//Game Loop that updates the screen and FPS constantly (Taken from RealTutsGML Youtube Channel)
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 50.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta>=1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer +=1000;
				//System.out.println("FPS:" + frames + " TICKS: "+ updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void update() {
		gameObjects.update(cam);
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			final int NUM_BUFFERS = 3;
			this.createBufferStrategy(NUM_BUFFERS);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////Actual Graphics///////
		g.setColor(new Color(78, 35, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2d.translate(cam.getX(),cam.getY());
		
		gameObjects.render(g);
		g2d.translate(-cam.getX(),-cam.getY());
		////////////
		g.dispose();
		bs.show();
	}
	private void LoadImageMap(BufferedImage image){
		int w = image.getWidth();
		int h = image.getHeight();
		for(int row = 0; row < h; row++){
			if(row>=24)
				break;
			for(int col = 0; col < w; col++){
				if(col>= 29)
					break;
			
				int pixel = image.getRGB(col, row);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
			
				if(red == 255 && green == 255 && blue == 255){
					gameObjects.addObject(new Bush(col*this.tileWidth, row*this.tileHeight, ObjectId.Bush));
					mainMap.loadMap(row, col,false);
				}
				else if(red == 0 && green == 255 && blue == 0){
					gameObjects.addObject(new Tree(col*this.tileWidth, row*this.tileHeight, ObjectId.Tree));
					mainMap.loadMap(row, col,true);
				}
				else if(red == 64 && green == 64 && blue == 64){
					gameObjects.addObject(new Tombstone(col*this.tileWidth, row*this.tileHeight, ObjectId.Tombstone));
					mainMap.loadMap(row, col,false);
				}
				else if(red == 192 && green == 192 && blue == 192){
					gameObjects.addObject(new Cross(col*this.tileWidth, row*this.tileHeight, ObjectId.Cross));
					mainMap.loadMap(row, col,false);
				}
				else if(red == 255 && green == 0 && blue == 0){
					gameObjects.addObject(new AIChar(col*this.tileWidth, row*this.tileHeight, ObjectId.NormalZombie));
				}
				else if(red == 255 && green == 106 && blue == 0){
					gameObjects.startX = col*this.tileWidth;
					gameObjects.startY = row*this.tileHeight;
				}
			}
		}
	}	
}


class Tree extends GameObject{

	public Tree(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/tree.png","tree");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
}
class Bush extends GameObject{

	public Bush(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/bush.png","bush");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
}
class Tombstone extends GameObject{

	public Tombstone(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/tombstone.png","tombstone");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
}
class Cross extends GameObject{

	public Cross(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/cross.png","cross");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
}