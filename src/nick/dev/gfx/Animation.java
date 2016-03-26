package nick.dev.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nick.dev.base.Handler;
import nick.dev.maps.Map;

/**
 * This class is an object used to store animations. These are stored in the
 * frames BufferedImage array.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Animation {
	
	public enum LoopType {
		REPEAT, PINGPONG, NONE
	}

	private int speed, index;
	private int timer;
	private BufferedImage[] frames;
	private LoopType loopType;
	
	private boolean finished = false;

	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
		this.index = 0;
		this.timer = 0;
		
		this.loopType = LoopType.REPEAT;
	}
	
	public Animation(int speed, BufferedImage[] frames, LoopType loopType) {
		this.speed = speed;
		this.frames = frames;
		this.index = 0;
		this.timer = 0;
		
		this.loopType = loopType;
	}

	public void update() {
		timer++;

		if (timer > speed) {
			index++;
			timer = 0;
			if (index >= frames.length)
				
				switch(this.loopType) {
				case REPEAT:
					index = 0;
					break;
				case PINGPONG:
					break;
				case NONE:
					this.finished = true;
				}
		}
	}

	public void render(Graphics g, float x, float y) {
		int xPos = (int) (x - Handler.getGameCamera().getxOffset());
		int yPos = (int) (y - Handler.getGameCamera().getyOffset());
		g.drawImage(frames[index], xPos, yPos, Map.TileWidth, Map.TileHeight, null);
	}

	public void render(Graphics g, float x, float y, int width, int height) {
		g.drawImage(frames[index], (int)x, (int)y, width, height, null);
	}
	
	public boolean isFinished() {
		return this.finished;
	}

}
