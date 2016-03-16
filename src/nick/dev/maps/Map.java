package nick.dev.maps;

import java.awt.Graphics;
import java.util.HashMap;

import nick.dev.base.Handler;
import nick.dev.gfx.Assets;
import nick.dev.tiles.Tile;

/**
 * Map class is responsible for storing, updating, and displaying, map data
 * for the World. 
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Map {

	public static int TileWidth = 64;
	public static int TileHeight = 64;
	
	private static final HashMap<Integer, Tile> tileData;
	static {
		tileData = new HashMap<Integer, Tile>();
		tileData.put(0, new Tile(0, Assets.grass, false));
		tileData.put(1, new Tile(1, Assets.dirt, false));
		tileData.put(2, new Tile(2, Assets.stone, true));
		tileData.put(3, new Tile(3, Assets.sand, false));
	}
	
	private Integer mapWidth = 0;
	private Integer mapHeight = 0;
	
	private int[][] mapData;
	
	public Map(String newMapData, Integer width, Integer height) {
		this.mapData = new int[width][height];
		
		this.mapWidth = width;
		this.mapHeight = height;
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				this.mapData[x][y] = Integer.parseInt(newMapData.split("\\|")[(x + y * width)]);
			}
		}
	}
	
	public void update() {
		// Update all tiles.
	}
	
	public void render(Graphics g) {
		
		// This calculation ensures that only tiles in the camera view are
		// rendered.
		float xOff = Handler.getGameCamera().getxOffset();
		float yOff = Handler.getGameCamera().getyOffset();
		
		int xStart = (int) Math.max(0, xOff / Map.TileWidth);
		int xEnd = (int) Math.min(this.mapWidth, (xOff + Handler.getWidth()) / Map.TileWidth + 1);
		int yStart = (int) Math.max(0, yOff / Map.TileHeight);
		int yEnd = (int) Math.min(this.mapHeight, (yOff + Handler.getHeight()) / Map.TileHeight + 1);
		
		for (int x = xStart; x < xEnd; ++x) {
			for (int y = yStart; y < yEnd; ++y) {
				tileData.get(mapData[x][y]).render(g, (int) (x * Map.TileWidth - xOff), (int) (y * Map.TileHeight - yOff));
			}
		}	
	}
	
	public boolean tileIsSolid(Integer x, Integer y) {
		return tileData.get(mapData[x][y]).isSolid();
	}
}
