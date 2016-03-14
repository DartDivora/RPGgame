package nick.dev.states;

import java.awt.Graphics;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;
import nick.dev.worlds.World;

public class GameState extends State {

	// private Player player;
	private World world;

	public GameState(StateManager stateManager) {
		super(stateManager);
		world = new World(Utilities.getPropValue("world3", Utilities.getPropFile()));
		Handler.setWorld(world);
		// player = new Player(handler, 0, 0);
		// world = new World("res/worlds/world1.json");
	}

	@Override
	public void update() {
		world.update();
		if (Handler.getKeyManager().keyIsPressed(Keys.Space)) {
			this.stateManager.changeState(Types.Battle);
		}
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}
	
	@Override
	public void onEnter() {
		Handler.getAudioManager().playTrack(Tracks.Overworld);
	}
	
	@Override
	public void onExit() {
		Handler.getAudioManager().stopCurrentTrack();
	}

}
