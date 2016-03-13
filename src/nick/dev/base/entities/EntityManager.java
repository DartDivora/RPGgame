package nick.dev.base.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import nick.dev.base.Handler;
import nick.dev.base.entities.NPCs.NPC;

public class EntityManager {

	private Handler handler;
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
	
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		//hardcoded Gnoll and NPC, forgive me.
		Gnoll g = new Gnoll(handler, 100, 250, 64, 64);
		NPC n = new NPC(handler, 300, 400, 64, 64);
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

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
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
