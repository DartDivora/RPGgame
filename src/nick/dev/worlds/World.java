package nick.dev.worlds;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashMap;

import nick.dev.base.Handler;
import nick.dev.base.entities.EntityManager;
import nick.dev.base.entities.Player;
import nick.dev.gfx.Assets;
import nick.dev.maps.Map;
import nick.dev.tiles.Tile;
import nick.dev.utilities.Utilities;

/**
 * This class contains the world the player is rendered on.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class World {

	private int width, height;
	private int spawnX, spawnY;
	
	private Map currentMap;
	
	private HashMap<Integer, Tile> tileMap;

	String[] keys = new String[7];
	String[] worldResults;
	Integer chanceOfBattle = null;

	// Entities
	private EntityManager entityManager;

	public World(String path) {

		Handler.setWorld(this);

		worldResults = new String[7];
		entityManager = new EntityManager(new Player(100, 100));
		keys[0] = "mapWidth";
		keys[1] = "mapHeight";
		keys[2] = "spawnX";
		keys[3] = "spawnY";
		keys[4] = "worldTiles";
		keys[5] = "worldEntities";
		keys[6] = "chanceOfBattle";
		worldResults = Utilities.getFromJSONObject(path, keys);

		loadWorld(path);

		Utilities.Debug("spawnX: " + spawnX + " spawnY: " + spawnY);
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
		entityManager.getPlayer().setBattleChance(Integer.parseInt(worldResults[6]));
	}

	public void update() {
		currentMap.update();
		entityManager.update();
	}

	public void render(Graphics g) {
		currentMap.render(g);
		entityManager.render(g);
	}

	private void loadWorld(String path) {
		String[] worldEntities;
		worldEntities = worldResults[5].split(",");

		Utilities.Debug(Arrays.toString(worldResults));
		Utilities.Debug(Arrays.toString(worldEntities));
		width = Integer.parseInt(worldResults[0]);
		height = Integer.parseInt(worldResults[1]);
		spawnX = Integer.parseInt(worldResults[2]);
		spawnY = Integer.parseInt(worldResults[3]);
		
		currentMap = new Map(worldResults[4], width, height);
		
		for (int i = 0; i < worldEntities.length; i++) {
			String[] entity = worldEntities[i].split("\\|");
			Utilities.Debug(entity[i]);
			// entityManager.addEntity(new Tree(Handler,
			// Integer.parseInt(entity[1]), Integer.parseInt(entity[2])));
		}
	}
	
	public boolean tileIsSolid(Integer x, Integer y) {
		return currentMap.tileIsSolid(x, y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public HashMap<Integer, Tile> getTileMap() {
		return tileMap;
	}
}
