package nick.dev.entities;

import nick.dev.base.Handler;
import nick.dev.combat.Stats;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;

/******************************************************
 * Player class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *****************************************************/
public class Player extends Entity {

	/*****************************************************
	 * Constructor. Sets position and initializes animations.
	 *****************************************************/
	public static Stats stats = new Stats();

	public static Stats getStats() {
		return Player.stats;
	}

	private float moveSpeed = 3.5f;

	/*****************************************************
	 * Constructor. Sets position and initializes animations.
	 *****************************************************/
	public Player(float x, float y) {
		super(x, y);

		this.name = "AlexSanft";

		// Initialize all of the animations.
		this.animations[Direction.Up.getValue()] = new Animation(this.animSpeed, Assets.player_up);
		this.animations[Direction.Right.getValue()] = new Animation(this.animSpeed, Assets.player_right);
		this.animations[Direction.Down.getValue()] = new Animation(this.animSpeed, Assets.player_down);
		this.animations[Direction.Left.getValue()] = new Animation(this.animSpeed, Assets.player_left);

		this.facingDirection = Direction.Down;
	}

	/*****************************************************
	 * Update called every frame. Handles movement and interaction with other
	 * entities.
	 *****************************************************/
	@Override
	public void update() {
		super.update();

		this.doMovement();
	}

	/*****************************************************
	 * Handles the movement based on inputs for the player character. Checks for
	 * collisions.
	 *****************************************************/
	private void doMovement() {

		// Get inputs and move based on them.
		if (Handler.getKeyManager().keyIsDown(Keys.Up)) {
			this.facingDirection = Direction.Up;
			this.y -= this.moveSpeed;
			
		} if (Handler.getKeyManager().keyIsDown(Keys.Right)) {
			this.facingDirection = Direction.Right;
			this.x += this.moveSpeed;
			
		} if (Handler.getKeyManager().keyIsDown(Keys.Down)) {
			this.facingDirection = Direction.Down;
			this.y += this.moveSpeed;
			
		} if (Handler.getKeyManager().keyIsDown(Keys.Left)) {
			this.facingDirection = Direction.Left;
			this.x -= this.moveSpeed;
		}

		this.resolveEntityCollisions();
		this.resolveMapCollisions();
	}
}
