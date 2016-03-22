package nick.dev.entities;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.utilities.Utilities;

/**
 * This class is the basis for all NPCs. Extends the Creature class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class NPC extends Entity {
	
	/**************************************************************
	 * Stores the data for the NPCs in the game. Works the same as
	 * the Monster container.
	 **************************************************************/
	private static HashMap<String, NPC> npcData;
	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("npcJSON"));
		
		npcData = gson.fromJson(JSONString, new TypeToken<HashMap<String, NPC>>(){}.getType());
		
		for (Entry<String, NPC> entry : npcData.entrySet()) {
			entry.getValue().toString();
		}
	}
	/*************************************************************/

	private float moveSpeed = 3.5f;
	private Integer dialogID;

	/*****************************************************
	 * Constructor. Sets position and initializes animations.
	 *****************************************************/
	public NPC(float x, float y) {
		super(x, y);
		
		// Initialize all of the animations.
		this.animations[Direction.Up.ordinal()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_UP);
		this.animations[Direction.Right.ordinal()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_RIGHT);
		this.animations[Direction.Down.ordinal()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_DOWN);
		this.animations[Direction.Left.ordinal()] = new Animation(this.animSpeed, Assets.DEFAULT_ANIM_LEFT);
		
		this.facingDirection = Direction.Down;
	}
	
	/*****************************************************
	 * Update called every frame. Handles movement and 
	 * interaction with other entities.
	 *****************************************************/
	@Override
	public void update() {
		super.update();
	}
	
	/*****************************************************
	 * Initiate dialog state with dialogID as the argument.
	 *****************************************************/
	@Override
	public void onInteract() {
		
	}

}
