package nick.dev.base.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.base.entities.NPCs.NPC;
import nick.dev.tiles.Tile;
import nick.dev.utilities.Utilities;

/**
 * This class manages all entities in-game through an ArrayList of entities.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class EntityManager {
	
	/**************************************************************
	 * Stores the data for the monsters in the game. Reads from
	 * a JSON file and loads the data. When creating a new monster
	 * in the game, it will use this collection as the template.
	 **************************************************************/
	private static HashMap<String, Monster> monsterData;
	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("monsterJSON"));
		
		monsterData = gson.fromJson(JSONString, new TypeToken<HashMap<String, Monster>>(){}.getType());
		
		for (Entry<String, Monster> entry : monsterData.entrySet()) {
			entry.getValue().printStats();
		}
	}
	/**************************************************************/
	
	private Monster[] monsters;
	private Player player;
	
	/**************************************************************
	 * Adds a new monster to the collection based off of monsterData
	 **************************************************************/
	public void createNewMonster(String name, Integer x, Integer y) {
		Integer newIndex = monsters.length;
		monsters[newIndex] = new Monster(monsterData.get(name));
	}

	
	/**************************************************************
	 * Old section
	 **************************************************************/
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
				return -1;
			} else {
				return 1;
			}
		}
	};
	
	public EntityManager(Player player) {
		this.player = player;
		// hardcoded Gnoll and NPC, forgive me.
		Gnoll g = new Gnoll(100, 250, 64, 64);
		NPC n = new NPC(300, 400, 64, 64);
		entities = new ArrayList<Entity>();
		addEntity(player);
		addEntity(g);
		addEntity(n);
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
		}
		entities.sort(renderSorter);
	}

	

	public void render(Graphics g) {
		for (Entity e : entities) {
			e.render(g);
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
}
