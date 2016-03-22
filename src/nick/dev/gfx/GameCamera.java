package nick.dev.gfx;

import nick.dev.base.Handler;
import nick.dev.entities.Entity;
import nick.dev.maps.Map;

/**
 * This class is the GameCamera that will follow the player.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class GameCamera {

	private float xOffset, yOffset;
	private Entity target;

	public GameCamera() {
		this.xOffset = 0;
		this.yOffset = 0;
	}
	
	public GameCamera(Entity e) {
		this.target = e;
	}
	
	public void update() {
		xOffset = this.target.getX() - Handler.getWidth() / 2 + Map.TileWidth / 2;
		yOffset = this.target.getY() - Handler.getHeight() / 2 + Map.TileHeight / 2;
		//checkBlankSpace();
	}

	public void checkBlankSpace() {
		if (xOffset < 0) {
			xOffset = 0;
		} else if (xOffset > Handler.getWorld().getWidth() * Map.TileWidth - Handler.getWidth()) {
			xOffset = Handler.getWorld().getWidth() * Map.TileWidth - Handler.getWidth();
		}
		if (yOffset < 0) {
			yOffset = 0;
		} else if (yOffset > Handler.getWorld().getHeight() * Map.TileHeight - Handler.getHeight()) {
			yOffset = Handler.getWorld().getHeight() * Map.TileHeight - Handler.getHeight();
		}

	}

	public void setTarget(Entity target) {
		this.target = target;
	}

//	public void move(float xAmt, float yAmt) {
//		xOffset += xAmt;
//		yOffset += yAmt;
//		checkBlankSpace();
//	}

	public float getxOffset() {
		return xOffset;
	}
	public float getyOffset() {
		return yOffset;
	}
}
