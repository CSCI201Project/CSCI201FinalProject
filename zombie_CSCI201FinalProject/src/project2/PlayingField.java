package project2;

import imageProcessing.BufferedImageLoader;
import imageProcessing.Camera;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;

import networking.ServerBullet;
import networking.ServerBulletList;
import networking.ZombieGameClient;
import networking.ZombieGameServer;
import weapons.Ammo;
import weapons.Bullet;
import weapons.Gun;
import weapons.Rifle;
import characters.AIChar;
import characters.PlayerChar;
import characters.Zombie;
import AStarSearch.AStarTest;

public class PlayingField extends Canvas implements Runnable {
	private GameWindow parent;
	boolean done = false;
	
	static public int windowWidth;
	static public int windowHeight;
	private TimerThread timer;
	private GameObjectHandler gameObjects;
	private BufferedImage level = null;
	private AStarTest mainMap;
	public static ArrayList<AIChar> AIList = new ArrayList<>();
	private Camera cam;
	private Random rand;
	private int counter = 0;
	private int spawnDelay = 5;
	private int alphaSpawnDelay = 10;
	static public int tileHeight = 55;
	static public int tileWidth = 55;
	
	private boolean isHost;
	private ZombieGameServer server;
	private ZombieGameClient client;
	
	private int aiIterator = 0;
	
	public PlayingField(TimerThread timer) {
		this.timer = timer;		
		this.rand = new Random();				
		this.setBackground(Color.BLACK);
		gameObjects = new GameObjectHandler(tileWidth,tileHeight);
		gameObjects.setPlayingField(this);
		this.mainMap = new AStarTest(new AIChar(0,0,ObjectId.NormalZombie)); //This AI is not actually used
		
		for(int i = 0; i<mainMap.width; i++) {
			for(int j = 0; j<mainMap.height; j++) {
				mainMap.mapTiles[i][j] = true;
			}
		}
	}
	public void setGameWindow(GameWindow gw) {
		this.parent = gw;
	}
	
	public GameObjectHandler getGameObjectHandler(){
		return this.gameObjects;
	}
	public static void removeKeyListener(PlayingField pf, PlayerChar player){
		pf.removeKeyListener(player.getKeyAdapter());
	}
	public static void addKeyListener(PlayingField pf, PlayerChar player){
		pf.addKeyListener(player.getKeyAdapter());
	}
	
	private void spawnZombies(){
		if(this.gameObjects.canSpawnZombies()){
			int numZombies = 2;
			for(int i = 0; i < numZombies; i++){
				boolean zombieSpawned = false;
				while(!zombieSpawned){
					int col = this.rand.nextInt(this.mainMap.width);
					int row = this.rand.nextInt(this.mainMap.height);
					
					if(mainMap.mapTiles[col][row] == true){
						zombieSpawned = true;
						Zombie tempZombie = new Zombie(col*this.tileWidth, row*this.tileHeight, ObjectId.NormalZombie);
						tempZombie.iterator = aiIterator;
						aiIterator++;
						
						this.gameObjects.addObject(tempZombie);
						tempZombie.setMap(this.mainMap.mapTiles);
						tempZombie.setTarget(this.gameObjects.player);
						System.out.println("ZOMBIE ADDED!!");
						
						Thread t = new Thread(tempZombie);
						t.start();
					}
				}
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
		PlayingField.addKeyListener(this, gameObjects.player);
		gameObjects.startAI();
		
		/*for(int i = 0; i < this.server.getConnections(); i++)
			gameObjects.addPlayer(new PlayerChar("fuck", gameObjects.startX, gameObjects.startY, ObjectId.HumanSurvivor));*/
		
		if(this.isHost)
			gameObjects.startAI();
	}
	
	public void transferBullet(Bullet b) {
		this.client.generateBullet(b);
	}
	
	public String getPlayerName() {
		if(this.isHost) return this.server.getPlayerName();
		else return this.client.getPlayerName();
	}
	public void initializePlayer(PlayerChar pc) {
		if(this.isHost) {
			this.server.initializePlayer(pc);
		} else {
			this.client.initializePlayer(pc);
		}
	}
	public void syncPlayer(PlayerChar pc) {
		if(this.isHost) {
			this.server.syncPlayer(pc);
		} else {
			this.client.syncPlayer(pc);
		}
	}

	public void setServer(ZombieGameServer zgs) {
		this.isHost = true;
		this.server = zgs;
		
		gameObjects.isHost = true;
	}
	public void setClient(ZombieGameClient zgc, ZombieGameServer zgs) {
		this.isHost = false;
		this.client = zgc;
		this.server = zgs;
		
		gameObjects.isHost = false;
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
		
		while(!done){
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
		this.counter++;
		if(this.counter == (this.spawnDelay*60)) {
			this.spawnZombies();
			this.counter = 0;
		}
		
		gameObjects.update(cam);
		
		if(isHost) {			
			server.updateObjects(gameObjects.getBullets());
			server.updateObjects(gameObjects.getObjects());
		}
		
		if(timer.timerDone()) {
			done = true;
			for(GameObject g : gameObjects.getObjects()) {
				if(g.getID() == ObjectId.NormalZombie || g.getID() == ObjectId.AlphaZombie) {
					AIChar ai = (AIChar) g;
					g.turnOff();
				}
			}
			
			//if(survivors)
			parent.endGame(true);
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			final int NUM_BUFFERS = 3;
			this.createBufferStrategy(NUM_BUFFERS);
			this.timer.start();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////Actual Graphics///////
		g.setColor(new Color(78, 35, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2d.translate(cam.getX(),cam.getY());
		
		gameObjects.render(g);//Most rendering happens here
		g2d.translate(-cam.getX(),-cam.getY());
		
		//Time Display Information
		g.setFont(new Font("Arial",Font.BOLD, 18));
		g.setColor(Color.WHITE);
		if(timer.numSecs < 60)
			g.setColor(Color.red);
		if(timer.secsRemaining < 10)
			g.drawString(this.timer.minRemaining+":0"+this.timer.secsRemaining, this.getWidth()/2 - 10, 45);
		else
			g.drawString(this.timer.minRemaining+":"+this.timer.secsRemaining, this.getWidth()/2 - 10, 45);
		////////////
		
		g.dispose();
		g2d.dispose();
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
					AIChar ai = new AIChar(col*this.tileWidth, row*this.tileHeight, ObjectId.NormalZombie);
					ai.iterator = aiIterator;
					aiIterator++;
					
					AIList.add(ai);
					gameObjects.addObject(ai);
				}
				else if (red == 0 && green == 0 && blue == 255){
					gameObjects.addObject(new Ammo(col*this.tileWidth, row*this.tileHeight, ObjectId.Ammo));
				}
				else if (red == 0 && green ==255  && blue == 255){
					gameObjects.addObject(new Rifle(col*this.tileWidth, row*this.tileHeight, ObjectId.Rifle));
				}
				else if (red == 255 && green == 0 && blue == 255){
					gameObjects.addObject(new Gun(col*this.tileWidth, row*this.tileHeight, ObjectId.Shotgun));
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
		this.width = 100;
		this.height = 100;
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/tree.png","tree");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
		
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 100, 100);
	}
}
class Bush extends GameObject{

	public Bush(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 45;
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/bush.png","bush");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 45);
	}
}
class Tombstone extends GameObject{

	public Tombstone(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 50;
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/tombstone.png","tombstone");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 50);
	}
}
class Cross extends GameObject{

	public Cross(double x, double y,ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 50;
	}

	public void render(Graphics g) {
		ImageIcon icon = new ImageIcon("images/cross.png","cross");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 50);
	}
}