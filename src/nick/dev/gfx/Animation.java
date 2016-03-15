package nick.dev.gfx;

import java.awt.image.BufferedImage;

/**
 * This class is an object used to store animations. These are stored in the
 * frames BufferedImage array.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Animation {

	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;

	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}

	public void update() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (timer > speed) {
			index++;
			timer = 0;
			if (index >= frames.length)
				index = 0;
		}
	}

	public BufferedImage getCurrentFrame() {
		return frames[index];
	}

}
