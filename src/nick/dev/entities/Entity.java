package nick.dev.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.maps.Map;
import nick.dev.utilities.Utilities;
import nick.dev.worlds.World;

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
	protected void resolveCollision(Rectangle collision) {
		
		if (collision != null) {
			// if we collided on the left or right, not the top or bottom.
			if (collision.getWidth() < collision.getHeight()) {
				if ((int) this.x + Map.TileWidth == collision.getMaxX()) {
					// Collision on the left
					this.x -= collision.getWidth();
					this.x = (int)this.x;
				} else {
					// Collision on the right
					this.x += collision.getWidth();
					this.x = (int)this.x;
				}
			} else {
				if ((int) this.y + Map.TileHeight == collision.getMaxY()) {
					// Collision on the top
					this.y -= collision.getHeight();
					this.y = (int)this.y;
				} else {
					// Collision on the bottom
					this.y += collision.getHeight();
					this.y = (int)this.y;
				}
			}
		}
	}
	
	/*****************************************************
	 * Resolves collisions with the map.
	 *****************************************************/
	protected void resolveMapCollisions() {
		Rectangle bounds = this.getBoundingBox();
		World world = Handler.getWorld();
		
		
		// Checks all four corners - if the block there is solid, 
		// check if we're colliding with it. If we are, resolve it.
		int[] checkX = new int[4];
		int[] checkY = new int[4];
		
		checkX[0] = bounds.x / Map.TileWidth;
		checkX[1] = (int)bounds.getMaxX() / Map.TileWidth;
		checkX[2] = (int)bounds.getMaxX() / Map.TileWidth;
		checkX[3] = bounds.x / Map.TileWidth;
		
		checkY[0] = bounds.y / Map.TileHeight;
		checkY[1] = bounds.y / Map.TileHeight;
		checkY[2] = (int)bounds.getMaxY() / Map.TileHeight;
		checkY[3] = (int)bounds.getMaxY() / Map.TileHeight;
		
		for (int i = 0; i < 4; ++i) {
			if (world.tileIsSolid(checkX[i], checkY[i])) {
				Rectangle tileBB = Utilities.tileToBoundingBox(checkX[i], checkY[i]);
				Rectangle collision = this.getBoundingBox().intersection(tileBB);
				this.resolveCollision(collision);
			}
		}
	}
	
	/*****************************************************
	 * Resolves collisions with other entities.
	 *****************************************************/
	protected void resolveEntityCollisions() {
		Rectangle collisionWithEntity = owner.isColliding(this);
		this.resolveCollision(collisionWithEntity);
	}
	
	/*****************************************************
	 * Gets the bounding box for the entity.
	 *****************************************************/
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
