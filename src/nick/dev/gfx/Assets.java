package nick.dev.gfx;

import java.awt.image.BufferedImage;

import nick.dev.utilities.Utilities;

/**
 * This class loads all images used for rendering.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Assets {
	private static int width, height;
	public static BufferedImage dirt, sand, grass, stone, tree, town;
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] DEFAULT_ANIM_DOWN, DEFAULT_ANIM_UP, DEFAULT_ANIM_LEFT, DEFAULT_ANIM_RIGHT;

	public static void init() {

		width = Integer.parseInt(Utilities.getPropValue("tileWidth"));
		height = Integer.parseInt(Utilities.getPropValue("tileHeight"));
		SpriteSheet tileSheet = new SpriteSheet(loadImage.loadImages(Utilities.getPropValue("tileSheet")));
		SpriteSheet characterSheet = new SpriteSheet(loadImage.loadImages(Utilities.getPropValue("characterSheet")));
		SpriteSheet monsterSheet = new SpriteSheet(loadImage.loadImages(Utilities.getPropValue("monsterSheet")));
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_right = new BufferedImage[2];
		player_left = new BufferedImage[2];
		DEFAULT_ANIM_DOWN = new BufferedImage[2];
		DEFAULT_ANIM_UP = new BufferedImage[2];
		DEFAULT_ANIM_LEFT = new BufferedImage[2];
		DEFAULT_ANIM_RIGHT = new BufferedImage[2];

		player_down[0] = characterSheet.crop(0, 0, 15, 17);
		player_down[1] = characterSheet.crop(16, 0, 15, 17);
		player_up[0] = characterSheet.crop(32, 0, 15, 17);
		player_up[1] = characterSheet.crop(48, 0, 15, 17);
		player_left[0] = characterSheet.crop(64, 0, 14, 17);
		player_left[1] = characterSheet.crop(78, 0, 14, 17);
		player_right[0] = characterSheet.crop(93, 0, 14, 17);
		player_right[1] = characterSheet.crop(108, 0, 14, 17);

		DEFAULT_ANIM_DOWN[0] = monsterSheet.crop(0, 0, 16, 16);
		DEFAULT_ANIM_DOWN[1] = monsterSheet.crop(16, 0, 16, 16);
		DEFAULT_ANIM_UP[0] = monsterSheet.crop(32, 0, 16, 16);
		DEFAULT_ANIM_UP[1] = monsterSheet.crop(48, 0, 16, 16);
		DEFAULT_ANIM_LEFT[0] = monsterSheet.crop(64, 0, 16, 16);
		DEFAULT_ANIM_LEFT[1] = monsterSheet.crop(80, 0, 16, 16);
		DEFAULT_ANIM_RIGHT[0] = monsterSheet.crop(96, 0, 16, 16);
		DEFAULT_ANIM_RIGHT[1] = monsterSheet.crop(112, 0, 16, 16);

		grass = tileSheet.crop(192, 0, width, height);
		dirt = tileSheet.crop(288, 0, width, height);
		stone = tileSheet.crop(240, 64, width, height);
		sand = tileSheet.crop(208, 0, width, height);
		tree = tileSheet.crop(208, 144, width, height * 2);
		town = tileSheet.crop(416, 16, width, height);
	}

}
