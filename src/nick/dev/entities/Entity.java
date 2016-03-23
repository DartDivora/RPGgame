package nick.dev.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.maps.Map;

/******************************************************
 * This abstract class is the base for any in-game entities 
 * rendered on the map.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *****************************************************/
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
	protected EntityManager owner;
	
	protected String name;
	protected float x, y;

	// Animation speed in display frames per frame of animation
	protected int animSpeed = 30;
	
	protected Animation[] animations = new Animation[4];
	protected Direction facingDirection;
	
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
	 * Check to see if the player is colliding with the map
	 * or with any other entities.
	 *****************************************************/
	protected boolean resolveCollisions(float newX, float newY, Integer xOff, Integer yOff) {
		// Check for collision with the map
		Integer destTileX = (int) Math.floor(newX  / Map.TileWidth) + xOff;
		Integer destTileY = (int) Math.floor(newY / Map.TileHeight) + yOff;
		
		boolean collidingWithMap = Handler.getWorld().tileIsSolid(destTileX, destTileY);
		Rectangle collisionWithEntity = owner.isColliding(this);
		
		if (collisionWithEntity != null) {
			// if we collided on the left or right, not the top or bottom.
			if (collisionWithEntity.getWidth() < collisionWithEntity.getHeight()) {
				if ((int) this.x + Map.TileWidth == collisionWithEntity.getMaxX()) {
					// Collision on the left;
					this.x -= collisionWithEntity.getWidth();
					this.x = (int)this.x;
				} else {
					// Collision on the right
					this.x += collisionWithEntity.getWidth();
					this.x = (int)this.x;
				}
			} else {
				if ((int) this.y + Map.TileHeight == collisionWithEntity.getMaxY()) {
					// Collision on the top;
					this.y -= collisionWithEntity.getHeight();
					this.y = (int)this.y;
				} else {
					// Collision on the bottom
					this.y += collisionWithEntity.getHeight();
					this.y = (int)this.y;
				}
			}
		}
		
		return collidingWithMap || (collisionWithEntity != null);
	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle((int)this.x, (int)this.y, Map.TileWidth, Map.TileHeight);
	}
	
	/*****************************************************
	 * Getters and Setters
	 *****************************************************/
	public void setOwner(EntityManager em) {
		this.owner = em;
	}
	
	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Direction getDirection() {
		return this.facingDirection;
	}
	
}
