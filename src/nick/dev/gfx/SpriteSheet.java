package nick.dev.gfx;

import java.awt.image.BufferedImage;

/**
 * This class is used to get a SubImage from a Sprite Sheet.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class SpriteSheet {

	private BufferedImage sheet;

	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}

	public BufferedImage crop(int x, int y, int width, int height) {

		return sheet.getSubimage(x, y, width, height);

	}

}
