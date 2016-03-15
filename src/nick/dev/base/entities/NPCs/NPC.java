package nick.dev.base.entities.NPCs;

import nick.dev.base.entities.Creature;

/**
 * This class is the basis for all NPCs. Extends the Creature class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class NPC extends Creature {

	public NPC(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setCanTalk(true);
	}

}
