package nick.dev.base;

import nick.dev.audio.AudioManager;
import nick.dev.base.entities.Player;
import nick.dev.display.Display;
import nick.dev.gfx.GameCamera;
import nick.dev.input.KeyManager;
import nick.dev.input.MouseManager;
import nick.dev.utilities.SaveManager;
import nick.dev.worlds.World;

public class Handler {

	private Game game;
	private World world;
	private AudioManager audioManager;
	private SaveManager saveManager;
	private Display display;

	public Handler(Game game) {
		this.game = game;
		audioManager = new AudioManager();
	}

	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}

	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}

	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}

	public int getWidth() {
		return game.getWidth();
	}

	public int getHeight() {
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Player getPlayer() {
		return this.getWorld().getEntityManager().getPlayer();
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public void setAudioManager(AudioManager audioManager) {
		this.audioManager = audioManager;
	}

	public SaveManager getSaveManager() {
		return saveManager;
	}

	public void setSaveManager(SaveManager saveManager) {
		this.saveManager = saveManager;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

}
