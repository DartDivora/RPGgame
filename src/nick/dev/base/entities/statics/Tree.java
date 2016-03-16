package nick.dev.base.entities.statics;

import java.awt.Graphics;

import nick.dev.base.Handler;
import nick.dev.gfx.Assets;
import nick.dev.maps.Map;

/**
 * This is a tree, that's about it.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Tree extends StaticEntity {

	public Tree(float x, float y) {
		super(x, y, Map.TileWidth, Map.TileHeight * 2);

		bounds.x = 10;
		bounds.y = (int) (height / 1.5f);
		bounds.width = width - 20;
		bounds.height = (int) (height - height / 1.5f);
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - Handler.getGameCamera().getxOffset()),
				(int) (y - Handler.getGameCamera().getyOffset()), width, height, null);

	}

}
