package networking;

import java.io.Serializable;
import java.util.Vector;

import weapons.Bullet;

public class ServerBulletList implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vector<ServerBullet> v;
	
	public ServerBulletList() {
		v = new Vector<ServerBullet>();
	}

	public void add(ServerBullet b) {
		v.add(b);
	}
	
	public Vector<ServerBullet> getServerBullets() {
		return v;
	}
}
