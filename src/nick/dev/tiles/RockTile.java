package nick.dev.tiles;

import nick.dev.gfx.Assets;

/**
 * Rock.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class RockTile extends Tile {

	public RockTile(int id) {
		super(Assets.stone, id);

	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
