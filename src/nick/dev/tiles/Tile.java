package nick.dev.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class will manage all tiles.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Tile {

	public static final int TILEWIDTH = 64, TILEHEIGHT = 64;

	protected BufferedImage texture;
	protected final int id;
	protected final boolean isSolid;

	public Tile(int id, BufferedImage texture, boolean isSolid) {
		this.texture = texture;
		this.id = id;
		this.isSolid = isSolid;
	}

	public void update() {

	}

	public boolean isSolid() {
		return isSolid;
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}

	public int getId() {
		return id;
	}
}
