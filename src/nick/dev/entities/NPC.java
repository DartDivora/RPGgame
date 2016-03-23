package nick.dev.entities;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;

/**
 * This class is the basis for all NPCs. Extends the Creature class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class NPC extends Entity {

	private float moveSpeed = 3.5f;

	/**************************************************************
	 * Stores the data for the NPCs in the game. Works the same as the Monster
	 * container.
	 **************************************************************/
	private static HashMap<String, NPC> npcData;
	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("npcJSON"));

		npcData = gson.fromJson(JSONString, new TypeToken<HashMap<String, NPC>>() {
		}.getType());

		for (Entry<String, NPC> entry : npcData.entrySet()) {
			entry.getValue().initialize();
		}
	}

	public static HashMap<String, NPC> getNPCData() {
		return npcData;
	}

	/*************************************************************/

	private Integer dialogueID;

	/*****************************************************
	 * Constructor. Probably won't do anything since we're using GSON.
	 *****************************************************/
	public NPC(float x, float y) {
		super(x, y);
	}

	/*****************************************************
	 * Handles the movement for NPCs. Checks for collisions.
	 *****************************************************/
	private void doMovement() {

		boolean DebugCollision = false;

		if (DebugCollision) {
			// Get inputs and move based on them.
			if (Handler.getKeyManager().keyIsDown(Keys.Up)) {
				this.facingDirection = Direction.Up;
				this.y -= this.moveSpeed;
			}
			if (Handler.getKeyManager().keyIsDown(Keys.Right)) {
				this.facingDirection = Direction.Right;
				this.x += this.moveSpeed;
			}
			if (Handler.getKeyManager().keyIsDown(Keys.Down)) {
				this.facingDirection = Direction.Down;
				this.y += this.moveSpeed;
			}
			if (Handler.getKeyManager().keyIsDown(Keys.Left)) {
				this.facingDirection = Direction.Left;
				this.x -= this.moveSpeed;
			}
		} else {
			int randomY = Utilities.getRandomNumber(-1, 1);
			int randomX = Utilities.getRandomNumber(-1, 1);

			switch (Utilities.getRandomNumber(1, 4)) {
			case 1:
				this.facingDirection = Direction.Up;
				break;
			case 2:
				this.facingDirection = Direction.Down;
				break;
			case 3:
				this.facingDirection = Direction.Left;
				break;
			case 4:
				this.facingDirection = Direction.Right;
				break;
			default:
				this.facingDirection = Direction.Down;
				break;
			}

			this.y += randomY;
			this.x += randomX;
		}

		this.resolveEntityCollisions();
		this.resolveMapCollisions();
	}

	/*****************************************************
	 * Called when gson creates the instances from the json file.
	 *****************************************************/
	public void initialize() {

		this.animSpeed = 30;

		// Initialize all of the animations.
		this.animations = new Animation[4];
		this.animations[Direction.Up.getValue()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_UP);
		this.animations[Direction.Right.getValue()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_RIGHT);
		this.animations[Direction.Down.getValue()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_DOWN);
		this.animations[Direction.Left.getValue()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_LEFT);

		this.facingDirection = Direction.Down;
		this.moveSpeed = 3.5f;
	}

	/*****************************************************
	 * Update called every frame. Just calls super's update, which updates the
	 * animation.
	 *****************************************************/
	@Override
	public void update() {
		super.update();
		this.doMovement();
	}

	/*****************************************************
	 * Initiate dialog state with dialogID as the argument.
	 *****************************************************/
	@Override
	public void onInteract() {
		System.out.println(dialogueID);
	}

	public Integer getDialogueID() {
		return this.dialogueID;
	}

}
