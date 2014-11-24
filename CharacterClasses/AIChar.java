package project2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class AIChar extends Character{

	public AIChar(double x, double y, ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
	}

	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int) y, 64, 32);
	}

	public void update(Vector<GameObject> objects) {
		
	}

}
