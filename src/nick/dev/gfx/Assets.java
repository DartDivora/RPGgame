package nick.dev.gfx;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import nick.dev.input.MouseManager;
import nick.dev.utilities.Utilities;

public class Assets {
	private static int width, height;
	public static BufferedImage dirt, sand, grass, stone, tree, town;
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] DEFAULT_ANIM_DOWN, DEFAULT_ANIM_UP, DEFAULT_ANIM_LEFT, DEFAULT_ANIM_RIGHT;

	public static void init() {

		width = Integer.parseInt(Utilities.getPropValue("tileWidth", Utilities.getPropFile()));
		height = Integer.parseInt(Utilities.getPropValue("tileHeight", Utilities.getPropFile()));
		// SpriteSheet sheet = new
		// SpriteSheet(loadImage.loadImages("/textures/sheet.png"));
		SpriteSheet tileSheet = new SpriteSheet(
				loadImage.loadImages(Utilities.getPropValue("tileSheet", Utilities.getPropFile())));
		SpriteSheet characterSheet = new SpriteSheet(
				loadImage.loadImages(Utilities.getPropValue("characterSheet", Utilities.getPropFile())));
		SpriteSheet monsterSheet = new SpriteSheet(
				loadImage.loadImages(Utilities.getPropValue("monsterSheet", Utilities.getPropFile())));
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_right = new BufferedImage[2];
		player_left = new BufferedImage[2];
		DEFAULT_ANIM_DOWN = new BufferedImage[2];
		DEFAULT_ANIM_UP = new BufferedImage[2];
		DEFAULT_ANIM_LEFT = new BufferedImage[2];
		DEFAULT_ANIM_RIGHT = new BufferedImage[2];

		// player_down[0] = tileSheet.crop(width * 4, 0, width, height);
		// player_down[1] = tileSheet.crop(width * 5, 0, width, height);
		// player_up[0] = tileSheet.crop(width * 6, 0, width, height);
		// player_up[1] = tileSheet.crop(width * 7, 0, width, height);
		// player_right[0] = tileSheet.crop(width * 4, height, width, height);
		// player_right[1] = tileSheet.crop(width * 5, height, width, height);
		// player_left[0] = tileSheet.crop(width * 6, height, width, height);
		// player_left[1] = tileSheet.crop(width * 7, height, width, height);

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
		tree = tileSheet.crop(208, 144, width, height * 2);
		sand = tileSheet.crop(208, 0, width, height);
		town = tileSheet.crop(416, 16, width, height);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Assets a = new Assets();
		a.init();
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(a.player_left[0])));
		frame.getContentPane().add(new JLabel(new ImageIcon(a.player_left[1])));
		frame.addMouseListener(new MouseManager());
		frame.pack();
		frame.setVisible(true);
	}

}
