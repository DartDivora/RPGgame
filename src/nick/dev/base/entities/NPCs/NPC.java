package nick.dev.base.entities.NPCs;

import nick.dev.base.Handler;
import nick.dev.base.entities.Creature;

public class NPC extends Creature {

	public NPC(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.setCanTalk(true);
	}

	@Override
	public void talkAction() {
		System.out.println("wat");
		handler.getGame().getGameState().setDialog("wat");
		// handler.getPlayer().warp(100, 100);
		handler.getPlayer().setTalk(false);
		// System.out.println("NPC X:" + this.getX());
		// System.out.println("NPC Y:" + this.getY());
		// System.out.println("Player X:" + handler.getPlayer().getX());
		// System.out.println("Player Y:" + handler.getPlayer().getY());
		// System.out.println("Tile Height: " + Tile.TILEHEIGHT);
		// System.out.println("Tile Width: " + Tile.TILEWIDTH);

	}

}
