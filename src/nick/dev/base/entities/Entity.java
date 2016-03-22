package nick.dev.base.entities;

import java.awt.Graphics;

import nick.dev.combat.Stats;
import nick.dev.gfx.Animation;

/**
 * This abstract class is the base for any in-game entities rendered on the map.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public abstract class Entity {
	
	/*****************************************************
	 * The directions which an entity could be facing. 
	 * Used to determine the animation that will be rendered.
	 *****************************************************/
	public enum Direction {
		Up(0), Right(1), Down(2), Left(3);
		
		private final int value;
		
		private Direction(int value) {
			this.value = value;
		}
		
		// Used for the index for animations.
		public int getValue() {
			return this.value;
		}
	}
	
	/*****************************************************
	 * Name, position, etc
	 *****************************************************/
	protected String name;
	protected float x, y;
	
	protected Animation[] animations = new Animation[4];
	protected Direction facingDirection;
	
	protected Stats stats;
	
	/*****************************************************
	 * Default constructor
	 *****************************************************/
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/*****************************************************
	 * Update is called every frame. Entity's method 
	 * updates the current animation.
	 *****************************************************/
	public void update() {
		Integer currAnim = facingDirection.getValue();
		this.animations[currAnim].update();
	}
	
	/*****************************************************
	 * Renders the animation every frame.
	 *****************************************************/
	public void render(Graphics g) {
		Integer currAnim = facingDirection.getValue();
		this.animations[currAnim].render(g, this.x, this.y);
	}
	
	/*****************************************************
	 * Called when the user interacts with this entity
	 *****************************************************/
	public void onInteract() {
		
	}
	
	
	
	/*****************************************************
	 * Getters and Setters
	 *****************************************************/
	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}
	
}
