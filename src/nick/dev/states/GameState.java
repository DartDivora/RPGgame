package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;

import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;
import nick.dev.worlds.World;

public class GameState extends State {

	// private Player player;
	private World world;

	public GameState(Handler handler) {
		super(handler);
		// world = new World(handler, "res/worlds/world2.json");
		world = new World(handler, Utilities.getPropValue("world3", Utilities.getPropFile()));
		handler.setWorld(world);
		// player = new Player(handler, 0, 0);
		// world = new World("res/worlds/world1.json");

	}

	@Override
	public void update() {
		world.update();
		// player.update();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);

		// System.out.println(this.getDialog());
		if (this.getDialog() != null) {
			f = new Font("arial", Font.BOLD, 25);
			g.setFont(f);
			g.drawString(this.getDialog(), 100, 100);
			if (handler.getKeyManager().keyIsPressed(Keys.Space)) {
				this.setDialog(null);
			}
		}
		// player.render(g);
	}

}
