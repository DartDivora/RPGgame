package nick.dev.base.entities;

import java.awt.Graphics;

import nick.dev.combat.Stats;
import nick.dev.maps.Map;

public class Monster extends Entity {
	
	private String name;

	public Monster(float x, float y) {
		// just getting it to work for now.
		super(x, y, Map.TileWidth, Map.TileHeight);
	}
	
	public Monster(Monster copyFrom) {
		super(copyFrom);
	}
	
	public void printStats() {
		this.stats.printStats();
		//System.out.println(this.x);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public Stats getStats() {
		return this.stats;
	}
	
}
