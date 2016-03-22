package nick.dev.entities;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;
import nick.dev.maps.Map;

/******************************************************
 * Player class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *****************************************************/
public class Player extends Entity {
	
	private float moveSpeed = 3.5f;

	/*****************************************************
	 * Constructor. Sets position and initializes animations.
	 *****************************************************/
	public Player(float x, float y) {
		super(x, y);
		
		// Initialize all of the animations.
		this.animations[Direction.Up.getValue()] = new Animation(this.animSpeed, Assets.player_up);
		this.animations[Direction.Right.getValue()] = new Animation(this.animSpeed, Assets.player_right);
		this.animations[Direction.Down.getValue()] = new Animation(this.animSpeed, Assets.player_down);
		this.animations[Direction.Left.getValue()] = new Animation(this.animSpeed, Assets.player_left);
		
		this.facingDirection = Direction.Down;
	}
	
	/*****************************************************
	 * Update called every frame. Handles movement and 
	 * interaction with other entities.
	 *****************************************************/
	@Override
	public void update() {
		super.update();
		
		this.doMovement();
	}
	
	/*****************************************************
	 * Handles the movement based on inputs for the player
	 * character. Checks for collisions.
	 *****************************************************/
	private void doMovement() {
		float newX = this.x;
		float newY = this.y;
		Integer xOffset = 0;
		Integer yOffset = 0;
		
		// Get inputs and move based on them.
		if (Handler.getKeyManager().keyIsDown(Keys.Up)) {
			this.facingDirection = Direction.Up;
			newY -= this.moveSpeed;
			
		} else if (Handler.getKeyManager().keyIsDown(Keys.Right)) {
			this.facingDirection = Direction.Right;
			newX += this.moveSpeed;
			xOffset = 1;
			
		} else if (Handler.getKeyManager().keyIsDown(Keys.Down)) {
			this.facingDirection = Direction.Down;
			newY += this.moveSpeed;
			yOffset = 1;
			
		} else if (Handler.getKeyManager().keyIsDown(Keys.Left)) {
			this.facingDirection = Direction.Left;
			newX -= this.moveSpeed;
		}
		
		// Check if we are colliding. x and y offset are confusing, 
		// so I want to change it eventually.
		if (!this.isColliding(newX, newY, xOffset, yOffset)) {
			this.x = newX;
			this.y = newY;
		}
	}
	/*****************************************************
	 * Check to see if the player is colliding with the map
	 * or with any other entities.
	 *****************************************************/
	private boolean isColliding(float newX, float newY, Integer xOff, Integer yOff) {
		// Check for collision with the map
		Integer destTileX = (int) Math.floor(newX  / Map.TileWidth) + xOff;
		Integer destTileY = (int) Math.floor(newY / Map.TileHeight) + yOff;
		
		boolean collidingWithMap = Handler.getWorld().tileIsSolid(destTileX, destTileY);
		boolean collidingWithEntity = owner.isColliding(this, (int)newX, (int)newY);
		
		return collidingWithMap || collidingWithEntity;
	}

}
