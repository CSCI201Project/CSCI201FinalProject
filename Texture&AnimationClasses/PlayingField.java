package project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

class PlayingField extends Canvas implements Runnable{
	private static final long serialVersionUID = 1666844355808226275L;
	private GameObjectHandler gameObjects;
	private int tileHeight = 32;
	private int tileWidth = 32;
	
	public PlayingField(){
		this.setBackground(Color.BLACK);
		gameObjects = new GameObjectHandler();
		
		this.addKeyListener(gameObjects.getController());
	}
	public void run() {
		//Game Loop that updates the screen and FPS constantly (Taken from RealTutsGML Youtube Channel)
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
				System.out.println("FPS:" + frames + " TICKS: "+ updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void update() {
		gameObjects.update();
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			final int NUM_BUFFERS = 3;
			this.createBufferStrategy(NUM_BUFFERS);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
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
		gameObjects.render(g);
		
		////////////
		g.dispose();
		bs.show();
	}
}
