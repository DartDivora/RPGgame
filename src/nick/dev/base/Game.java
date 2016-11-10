package nick.dev.base;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import nick.dev.display.Display;
import nick.dev.gfx.Assets;
import nick.dev.gfx.GameCamera;
import nick.dev.states.StateManager;
import nick.dev.utilities.Utilities;

/**
 * This class contains the main game loop.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Game implements Runnable {

	private Display display = null;
	private int width, height;
	public String title = null;
	private Thread thread = null;
	private boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	private StateManager stateManager;

	// Camera

	private GameCamera gameCamera;

	// Handler

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	private void init() {

		Handler.init(this);

		// Create display and attach the listeners.
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(Handler.getKeyManager());
		display.getCanvas().addMouseListener(Handler.getMouseManager());
		display.getCanvas().addMouseMotionListener(Handler.getMouseManager());

		// TODO: Make this not gross, init above and then setting display.
		Handler.setDisplay(display);

		// Initialize all of the assets for the game.
		Assets.init();

		stateManager = new StateManager();

	}

	private void update() {
		Handler.update();
		stateManager.update();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(2);
			return;
		}
		g = bs.getDrawGraphics();

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
		@SuppressWarnings("unused")
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
				// System.out.println("FPS: " + ticks);
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
