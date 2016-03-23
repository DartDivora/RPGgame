package nick.dev.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
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
	 * Gets the entity at the given ID. 
	 *****************************************************/
	public Entity getEntity(Integer ID) {
		Entity e = this.entityList.get(ID);
		return (e == null) ? null : e;
	}
	
	/*****************************************************
	 * Checks if an entity e is colliding with any others
	 * in the entityList. (x and y are the desired position,
	 * not the current one.)
	 *****************************************************/
	public Rectangle isColliding(Entity e) {
		
		for (Entity other : this.entityList) {
			if (!other.equals(e)) {
				if (e.getBoundingBox().intersects(other.getBoundingBox())) {
					return e.getBoundingBox().intersection(other.getBoundingBox());
				}
			}
		}
		return null;
	}
	/*****************************************************
	 * Checks if an entity e exists in the space given by
	 * x and y (width and height are Map.TileWidth/Height/2.
	 *****************************************************/
	public Integer entityExistsHere(Integer x, Integer y) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		
		for (Entity other : this.entityList) {
			if (!(other instanceof Player)) {
				left = x < other.getX() + Map.TileWidth/2;
				right = x + Map.TileWidth/2 > other.getX();
				top = y < other.getY() + Map.TileHeight/2;
				bottom = y + Map.TileHeight/2 > other.getY();
				
				if (left && right && top && bottom) {
					return this.entityList.indexOf(other);
				}
			}
		}
		
		return null;
	}
	
	/*****************************************************
	 * Returns reference to the player.
	 *****************************************************/
	public Player getPlayer() {
		for (Entity e : this.entityList) {
			if (e instanceof Player) {
				return (Player) e;
			}
		}
		
		return null;
	}
}