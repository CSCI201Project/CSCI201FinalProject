package weapons;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.ImageIcon;

import project2.GameObject;
import project2.ObjectId;

public class Ammo extends GameObject {
	public Ammo(double x, double y, ObjectId id) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 50;
		isOn = true;
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		ImageIcon icon = new ImageIcon("images/ammo_box_sprite.png","ammo");
		Image image = icon.getImage();
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	public void update(Vector<GameObject> objects) {}
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 50);
		//return null;
	}
}
