package nick.dev.entities;

import nick.dev.base.Handler;
import nick.dev.combat.Spell;
import nick.dev.combat.Stats;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;
import nick.dev.items.Item;

/******************************************************
 * Player class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *****************************************************/
public class Player extends Entity {

	Integer[] spells;

	/*****************************************************
	 * Static stats object, used in battles, etc.
	 *****************************************************/
	public static Stats stats = new Stats();
	static {
		Player.stats.setName("Alex Sanft");
		Player.stats.setMaxHP(20);
		Player.stats.setCurrentHP(20);
		Player.stats.setStrength(4);
		Player.stats.setDefense(2);
		Player.stats.setIntelligence(20);
		Player.stats.setDexterity(2);
		Player.stats.setLuck(10);
		Player.stats.setWisdom(5);
		Player.stats.setSpeed(100);
	}

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
		spells = new Integer[Spell.getSpellMap().size()];
		
		Item.getItemMap(); //Debug code
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

		float moveChangeX = 0;
		float moveChangeY = 0;

		// Get inputs and move based on them.

		if (Handler.getKeyManager().keyIsDown(Keys.Right)) {
			this.facingDirection = Direction.Right;
			moveChangeX += this.moveSpeed;
		}
		if (Handler.getKeyManager().keyIsDown(Keys.Left)) {
			this.facingDirection = Direction.Left;
			moveChangeX -= this.moveSpeed;
		}
		if (Handler.getKeyManager().keyIsDown(Keys.Up)) {
			this.facingDirection = Direction.Up;
			moveChangeY -= this.moveSpeed;
		}
		if (Handler.getKeyManager().keyIsDown(Keys.Down)) {
			this.facingDirection = Direction.Down;
			moveChangeY += this.moveSpeed;
		}

		// Pythagorean theorem to make sure diagonal movement is not
		// greater than horizontal or vertical movement would be.
		if (moveChangeX != 0 && moveChangeY != 0) {

			moveChangeX = (float) (moveChangeX / Math.sqrt(2.0));

			float ySign = Math.signum(moveChangeY);
			moveChangeY = moveChangeX;
			if (Math.signum(moveChangeY) != ySign) {
				moveChangeY *= -1;
			}
		}

		this.x += moveChangeX;
		this.y += moveChangeY;

		this.resolveEntityCollisions();
		this.resolveMapCollisions();
	}
}
