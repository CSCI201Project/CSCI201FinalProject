package project2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

//import AStarSearch.AStarTest;

class PlayingField extends Canvas implements Runnable{
	private static final long serialVersionUID = 1666844355808226275L;
	static public int windowWidth;
	static public int windowHeight;
	private GameObjectHandler gameObjects;
	//private AStarTest mainMap;
	private Camera cam;
	private int tileHeight = 32;
	private int tileWidth = 32;
	
	public PlayingField(){
		this.setBackground(Color.BLACK);
		gameObjects = new GameObjectHandler(tileWidth, tileHeight);
		
		//this.mainMap = new AStarTest();
	}
	public void run() {
		//Game Loop that updates the screen and FPS constantly (Taken from RealTutsGML Youtube Channel)
		gameObjects.init();
		windowWidth = this.getWidth();
		windowHeight = this.getHeight();
		this.cam = new Camera(0,0);
		this.addKeyListener(gameObjects.getController());
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
		for(int i = 0; i<this.getHeight();i+=tileHeight){
			g.drawLine(0, i, this.getWidth(), i);
		}
		for(int i = 0; i<this.getWidth();i+=tileWidth){
			g.drawLine(i, 0, i, this.getHeight());
		}
		g2d.translate(cam.getX(),cam.getY());
		
		gameObjects.render(g);
		g2d.translate(-cam.getX(),-cam.getY());
		////////////
		g.dispose();
		bs.show();
	}
}
