package nick.dev.base;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import nick.dev.display.Display;
import nick.dev.gfx.Assets;
import nick.dev.gfx.GameCamera;
import nick.dev.input.KeyManager;
import nick.dev.input.MouseManager;
import nick.dev.states.BattleState;
import nick.dev.states.GameState;
import nick.dev.states.MenuState;
import nick.dev.states.SettingsState;
import nick.dev.states.State;
import nick.dev.utilities.SaveManager;
import nick.dev.utilities.Utilities;

public class Game implements Runnable {

	private Display display = null;
	private int width, height;
	public String title = null;
	private Thread thread = null;
	private boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	private boolean leftMenu = false;
	private boolean inBattle = false;
	// private BufferedImage testImage;
	// private SpriteSheet sheet;

	// States

	private State gameState;
	private State menuState;
	@SuppressWarnings("unused")
	private State settingsState;
	private State battleState;

	// Input

	private KeyManager keyManager;
	private MouseManager mouseManager;

	// Camera

	private GameCamera gameCamera;

	// Handler
	private Handler handler;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}

	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		// display.getFrame().addMouseMotionListener(mouseManager);
		// display.getCanvas().addMouseMotionListener(mouseManager);
		Utilities.Debug(Arrays.toString(display.getCanvas().getMouseListeners()));
		Assets.init();
		handler = new Handler(this);
		handler.setSaveManager(new SaveManager());
		handler.setDisplay(display);
		gameCamera = new GameCamera(handler, 0, 0);

		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		settingsState = new SettingsState(handler);
		battleState = new BattleState(handler);

		State.setState(menuState);

		// testImage = loadImage.loadImages("/textures/sheet.png");
		// sheet = new SpriteSheet(testImage);
	}

	public State getGameState() {
		return gameState;
	}

	public State getMenuState() {
		return menuState;
	}

	public State getBattleState() {
		return battleState;
	}

	public void leaveMenu() {
		Utilities.Debug("Setting state to gameState!");
		State.setState(gameState);
		handler.getAudioManager().Track(Utilities.getPropValue("musicOverworld", Utilities.getPropFile()));
		handler.getAudioManager().play();
	}

	private void update() {
		keyManager.update();
		mouseManager.update();
		if (!menuState.isInMenu() && !isLeftMenu()) {
			Utilities.Debug("Leaving menu!");
			leftMenu = true;
			this.leaveMenu();
		}

		if (handler.getDisplay().isSaveGame()) {
			handler.getDisplay().setSaveGame(false);
			handler.getSaveManager().saveGame(handler, "res/saves/AlexSave.json");
		}
		if (handler.getDisplay().isLoadGame()) {
			handler.getDisplay().setLoadGame(false);
			handler.getSaveManager().loadGame(handler, "res/saves/AlexLoad.json");
		}

		if (handler.getWorld().getEntityManager().getPlayer().isGoToBattle() && !this.isInBattle()) {
			Utilities.Debug("Battle!!!!");
			this.setInBattle(true);
			handler.getMouseManager().resetXY();
			handler.getAudioManager().stop();
			handler.getAudioManager().Track(Utilities.getPropValue("musicBattle", Utilities.getPropFile()));
			handler.getAudioManager().play();
			battleState.setReturnState(gameState);
			State.setState(battleState);
		}

		if (State.getState() != null) {
			State.getState().update();
		}
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear Screen
		g.clearRect(0, 0, width, height);

		if (State.getState() != null) {
			State.getState().render(g);
		}

		// Draw here!
		// g.fillRect(0, 0, width, height);
		// g.drawRect(10, 50, 50, 70);
		// g.drawImage(sheet.crop(0, 0, 32, 32), 5, 5, null);
		// g.drawImage(sheet.crop(32, 0, 32, 32), 5, 5, null);
		// End Draw

		bs.show();
		g.dispose();
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public void run() {
		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}

			if (timer >= 1000000000) {
				Utilities.Debug("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop();

	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			Utilities.Debug("An exception occurred when stopping the thread!");
			e.printStackTrace();
		}
	}

	public boolean isLeftMenu() {
		return leftMenu;
	}

	public void setLeftMenu(boolean leftMenu) {
		this.leftMenu = leftMenu;
	}

	public boolean isInBattle() {
		return inBattle;
	}

	public void setInBattle(boolean inBattle) {
		this.inBattle = inBattle;
	}
}
