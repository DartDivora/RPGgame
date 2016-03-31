package nick.dev.base;

import nick.dev.utilities.Utilities;

/**
 * This class contains the main method to start the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Launcher {

	public static void main(String[] args) {
		Integer width;
		Integer height;
		// You can get the width and height of the current monitor using this...
		// GraphicsDevice gd =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		// Integer width = gd.getDisplayMode().getWidth();
		// Integer height = gd.getDisplayMode().getHeight();
		// Utilities.Debug(width);
		// Utilities.Debug(height);
		width = Integer.parseInt(Utilities.getPropValue("gameWidth"));
		height = Integer.parseInt(Utilities.getPropValue("gameHeight"));
		Game g = new Game(Utilities.getPropValue("gameTitle"), width, height);
		g.start();
	}

}
