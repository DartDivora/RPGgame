package nick.dev.gfx;

import nick.dev.base.Handler;
import nick.dev.base.entities.Entity;
import nick.dev.tiles.Tile;

/**
 * This class is the GameCamera that will follow the player.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class GameCamera {

	private float xOffset, yOffset;

	public GameCamera(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void checkBlankSpace() {
		if (xOffset < 0) {
			xOffset = 0;
		} else if (xOffset > Handler.getWorld().getWidth() * Tile.TILEWIDTH - Handler.getWidth()) {
			xOffset = Handler.getWorld().getWidth() * Tile.TILEWIDTH - Handler.getWidth();
		}
		if (yOffset < 0) {
			yOffset = 0;
		} else if (yOffset > Handler.getWorld().getHeight() * Tile.TILEHEIGHT - Handler.getHeight()) {
			yOffset = Handler.getWorld().getHeight() * Tile.TILEHEIGHT - Handler.getHeight();
		}

	}

	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - Handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - Handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}

	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}
