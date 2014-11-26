package project2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class AIChar extends Character{

	public AIChar(double x, double y, int width, int height, ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int) y, width, height);
	}

	public void update(Vector<GameObject> objects) {
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
