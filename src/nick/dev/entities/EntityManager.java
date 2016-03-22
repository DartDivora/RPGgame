package nick.dev.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map.Entry;

import nick.dev.maps.Map;

/******************************************************
 * This class manages all entities in-game through an ArrayList of entities.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *****************************************************/
public class EntityManager {
	
	// List of all entities.
	private ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	/*****************************************************
	 * Constructor.
	 *****************************************************/
	public EntityManager() {
		
		// Add all NPCs to entity list
		for (Entry<String, NPC> entry : NPC.getNPCData().entrySet()) {
			this.addEntity(entry.getValue());
			entry.getValue().setOwner(this);
		}
	}

	/*****************************************************
	 * Update all entities every frame.
	 *****************************************************/
	public void update() {
		for (Entity entity : this.entityList) {
			entity.update();
		}
	}
	
	/*****************************************************
	 * Draw all of the entities.
	 *****************************************************/
	public void render(Graphics g) {
		for (Entity entity : this.entityList) {
			entity.render(g);
		}
	}
	
	/*****************************************************
	 * Add an entity to the entityList.
	 *****************************************************/
	public boolean addEntity(Entity e) {
		e.setOwner(this);
		return this.entityList.add(e);
	}
	
	/*****************************************************
	 * Checks if an entity e is colliding with any others
	 * in the entityList. (x and y are the desired position,
	 * not the current one.
	 *****************************************************/
	public boolean isColliding(Entity e, Integer x, Integer y) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		
		for (Entity other : this.entityList) {
			if (!other.equals(e)) {
				left = x < other.getX() + Map.TileWidth;
				right = x + Map.TileWidth > other.getX();
				top = y < other.getY() + Map.TileHeight;
				bottom = y + Map.TileHeight > other.getY();
			}
		}
		
		return (left && right && top && bottom);
	}
}