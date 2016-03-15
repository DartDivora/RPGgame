package nick.dev.base;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import nick.dev.utilities.Utilities;

/**
 * This class contains the main method to start the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Launcher {

	public static void main(String[] args) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Integer width = gd.getDisplayMode().getWidth();
		Integer height = gd.getDisplayMode().getHeight();

		Utilities.Debug(width);
		Utilities.Debug(height);
		width = Integer.parseInt(Utilities.getPropValue("gameWidth", Utilities.getPropFile()));
		height = Integer.parseInt(Utilities.getPropValue("gameHeight", Utilities.getPropFile()));
		// width = 300;
		// height = 300;
		Game g = new Game(Utilities.getPropValue("gameTitle", Utilities.getPropFile()), width, height);
		g.start();
	}

}
