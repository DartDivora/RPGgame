package nick.dev.base.entities.NPCs;

import nick.dev.base.Handler;
import nick.dev.base.entities.Creature;

public class NPC extends Creature {

	public NPC(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setCanTalk(true);
	}

}
