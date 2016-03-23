package nick.dev.maps;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.base.Handler;
import nick.dev.tiles.Tile;
import nick.dev.utilities.Utilities;

/**
 * Map class is responsible for storing, updating, and displaying, map data for
 * the World.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Map {
	
	/**************************************************************
	 * Stores and initializes the data for all of the different tiles.
	 **************************************************************/
	private static HashMap<String, Tile> tileData;
	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("tileJSON"));
		
		tileData = gson.fromJson(JSONString, new TypeToken<HashMap<String, Tile>>(){}.getType());
		
		for (Entry<String, Tile> entry : tileData.entrySet()) {
			entry.getValue().initialize();
		}
	}
	/**************************************************************/

	public static int TileWidth = 64;
	public static int TileHeight = 64;

	private Integer mapWidth = 0;
	private Integer mapHeight = 0;
	private Integer[][] mapData;
	
	public Map(String newMapData, Integer width, Integer height) {
		this.mapData = new Integer[width][height];

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
				Tile t = tileData.get(mapData[x][y].toString());
				t.render(g, (int) (x * Map.TileWidth - xOff), (int) (y * Map.TileHeight - yOff));
			}
		}
	}

	public boolean tileIsSolid(Integer x, Integer y) {
		Tile tileToCheck = tileData.get(mapData[x][y].toString());
		return tileToCheck.isSolid();
	}
}
