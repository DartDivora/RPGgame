package nick.dev.worlds;

import java.awt.Graphics;
import java.util.Arrays;

import nick.dev.base.Handler;
import nick.dev.base.entities.EntityManager;
import nick.dev.base.entities.Player;
import nick.dev.tiles.Tile;
import nick.dev.utilities.Utilities;

public class World {

	private int width, height;
	private int spawnX, spawnY;
	private int[][] worldTiles;
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

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void update() {
		entityManager.update();
	}

	public void render(Graphics g) {
		// This calculation ensures that only tiles in the camera view are
		// rendered.
		int xStart = (int) Math.max(0, Handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width,
				(Handler.getGameCamera().getxOffset() + Handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, Handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height,
				(Handler.getGameCamera().getyOffset() + Handler.getHeight()) / Tile.TILEHEIGHT + 1);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - Handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILEHEIGHT - Handler.getGameCamera().getyOffset()));
			}
		}

		// Entities
		entityManager.render(g);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.grassTile;
		}

		Tile t = Tile.tiles[worldTiles[x][y]];
		if (t == null) {
			return Tile.dirtTile;
		}
		return t;
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
		worldTiles = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				worldTiles[x][y] = Integer.parseInt(worldResults[4].split("\\|")[(x + y * width)]);

			}
		}
		for (int i = 0; i < worldEntities.length; i++) {
			String[] entity = worldEntities[i].split("\\|");
			Utilities.Debug(entity[i]);
			// entityManager.addEntity(new Tree(Handler,
			// Integer.parseInt(entity[1]), Integer.parseInt(entity[2])));
		}

		Handler.setWorld(this);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
