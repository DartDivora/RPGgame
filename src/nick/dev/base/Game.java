package nick.dev.base;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import nick.dev.audio.AudioManager;
import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.dialog.DialogManager;
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
import nick.dev.states.StateManager;
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

	private KeyManager keyManager = new KeyManager();
	private MouseManager mouseManager = new MouseManager();
	private SaveManager saveManager;
	private DialogManager dialogManager;
	private StateManager stateManager;

	// Camera

	private GameCamera gameCamera;

	// Audio
	private AudioManager audioManager;
	// Handler
	//private Handler handler;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	private void init() {
		

		Handler.init(this);
		
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(Handler.getKeyManager());
		display.getFrame().addMouseListener(Handler.getMouseManager());
		display.getCanvas().addMouseListener(Handler.getMouseManager());
		
		Handler.setDisplay(display);
		
		Utilities.Debug(Arrays.toString(display.getCanvas().getMouseListeners()));
		Assets.init();
		
		stateManager = new StateManager();
		
		gameCamera = new GameCamera(0, 0);
	}

	private void update() {
		Handler.update();
		stateManager.update();
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

		stateManager.render(g);

		bs.show();
		g.dispose();
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
}
