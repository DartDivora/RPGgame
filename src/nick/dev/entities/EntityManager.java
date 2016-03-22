package nick.dev.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.tiles.Tile;
import nick.dev.utilities.Utilities;

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
		return this.entityList.add(e);
	}	
}