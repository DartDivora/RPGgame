package nick.dev.states;

import java.awt.Graphics;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;
import nick.dev.worlds.World;

public class OverworldState extends State {

	private World world;

	public OverworldState(StateManager stateManager) {
		super(stateManager);
		world = new World(Utilities.getPropValue("world3", Utilities.getPropFile()));
		Handler.setWorld(world);
	}

	@Override
	public void update() {
		world.update();
		if (Handler.getKeyManager().keyIsPressed(Keys.Space)) {
			this.stateManager.changeState(Types.Battle);
		}
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			this.stateManager.changeState(Types.Dialog);
		}
	}

	@Override
	public void render(Graphics g) {
		// Clear Screen
		g.clearRect(0, 0, Handler.getWidth(), Handler.getHeight());
		world.render(g);
	}

	@Override
	public void onEnter() {
		Handler.getAudioManager().playTrack(Tracks.Overworld);
	}

	@Override
	public void onExit() {
		//Handler.getAudioManager().stopCurrentTrack();
	}

}
