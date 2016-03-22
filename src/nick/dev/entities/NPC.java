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
			System.out.println(entry.getValue().animSpeed);
			entry.getValue().initialize();
		}
	}
	
	public static HashMap<String, NPC> getNPCData() {
		return npcData;
	}
	/*************************************************************/

	private float moveSpeed = 3.5f;
	private Integer dialogueID;

	/*****************************************************
	 * Constructor. Probably won't do anything since we're 
	 * using GSON.
	 *****************************************************/
	public NPC(float x, float y) {
		super(x, y);
	}
	
	/*****************************************************
	 * Called when gson creates the instances from the 
	 * json file.
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
	}
	
	/*****************************************************
	 * Update called every frame. Just calls super's update,
	 * which updates the animation.
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
		System.out.println("talking!");
	}

}
