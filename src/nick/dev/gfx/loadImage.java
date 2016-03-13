package nick.dev.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nick.dev.utilities.Utilities;

public class loadImage {

	public static BufferedImage loadImages(String path) {
		try {
			return ImageIO.read(loadImage.class.getResource(path));
		} catch (IOException e) {
			Utilities.Debug("Image at path: " + path + " could not be loaded!");
			e.printStackTrace();
			return null;
		}

	}

}
