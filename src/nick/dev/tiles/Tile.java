package nick.dev.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nick.dev.gfx.Assets;
import nick.dev.gfx.SpriteSheet;
import nick.dev.gfx.loadImage;
import nick.dev.maps.Map;
import nick.dev.utilities.Utilities;

/**
 * This class defines what a tile is and is responsible for drawing each one. It
 * can probably just be removed since the map is kind of handling that
 * functionality...but there will probably be a reason to keep it.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Tile {

	protected final boolean isSolid;
	protected final int cropX, cropY;
	protected BufferedImage texture;

	public Tile(int id, int cropX, int cropY, boolean isSolid) {
		this.isSolid = isSolid;
		this.cropX = cropX;
		this.cropY = cropY;
	}
	
	public void initialize() {
		this.texture = Map.getTileSheet().crop(cropX, cropY, Assets.getHeight(), Assets.getWidth());
	}

	public void update() {

	}

	public boolean isSolid() {
		return isSolid;
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(this.texture, x, y, Map.TileWidth, Map.TileHeight, null);
	}
}
