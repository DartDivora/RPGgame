package nick.dev.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nick.dev.maps.Map;

/**
 * This class defines what a tile is and is responsible for drawing each one.
 * It can probably just be removed since the map is kind of handling that 
 * functionality...but there will probably be a reason to keep it. 
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Tile {
	
	protected BufferedImage texture;
	protected final int id;
	protected final boolean isSolid;

	public Tile(int id, BufferedImage texture, boolean isSolid) {
		this.id = id;
		this.texture = texture;
		this.isSolid = isSolid;
	}

	public void update() {

	}

	public boolean isSolid() {
		return isSolid;
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, Map.TileWidth, Map.TileHeight, null);
	}

	public int getId() {
		return id;
	}
}
