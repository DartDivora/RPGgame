package nick.dev.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nick.dev.gfx.Assets;

/**
 * This class will manage all tiles.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Tile {

	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new Tile(0, Assets.grass, false);
	public static Tile dirtTile = new Tile(1, Assets.dirt, false);
	public static Tile stoneTile = new Tile(2, Assets.stone, true);
	public static Tile sandTile = new Tile(3, Assets.sand, false);

	public static final int TILEWIDTH = 64, TILEHEIGHT = 64;

	protected BufferedImage texture;
	protected final int id;
	protected final boolean isSolid;

	public Tile(int id, BufferedImage texture, boolean isSolid) {
		this.texture = texture;
		this.id = id;
		this.isSolid = isSolid;

		tiles[id] = this;
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
