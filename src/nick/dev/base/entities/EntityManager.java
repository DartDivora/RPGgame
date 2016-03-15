package nick.dev.base.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import nick.dev.base.entities.NPCs.NPC;

/**
 * This class manages all entities in-game through an ArrayList of entities.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class EntityManager {

	private Player player;
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

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
		}
		entities.sort(renderSorter);
	}

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
