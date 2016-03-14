package nick.dev.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import nick.dev.base.Handler;
import nick.dev.utilities.Utilities;

public class KeyManager implements KeyListener {

	public enum Keys {
		Up, Down, Left, Right, Space, Mute
	};

	private HashMap<Keys, Integer> keybinds;

	private boolean[] keys;
	private boolean[] keysAlreadyPressed;
	@SuppressWarnings("unused")
	private Handler handler;

	public KeyManager() {
		keys = new boolean[256];
		keysAlreadyPressed = new boolean[256];

		keybinds = new HashMap<Keys, Integer>();

		keybinds.put(Keys.Up, KeyEvent.VK_W);
		keybinds.put(Keys.Down, KeyEvent.VK_S);
		keybinds.put(Keys.Left, KeyEvent.VK_A);
		keybinds.put(Keys.Right, KeyEvent.VK_D);
		keybinds.put(Keys.Space, KeyEvent.VK_SPACE);
		keybinds.put(Keys.Mute, KeyEvent.VK_M);
	}

	public void update() {
	}

	public boolean keyIsPressed(Keys key) {
		int code = keybinds.get(key);
		if (keys[code] && !keysAlreadyPressed[code]) {
			keysAlreadyPressed[code] = true;
			return true;
		}
		return false;
	}

	public boolean keyIsDown(Keys key) {
		int code = keybinds.get(key);
		if (keys[code]) {
			return true;
		}

		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();
			if (!keys[code] && !keysAlreadyPressed[code])
				keys[code] = true;
		} catch (ArrayIndexOutOfBoundsException ex) {
		}

		Utilities.Debug("Pressed: " + e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			int code = e.getKeyCode();
			keys[code] = false;
			keysAlreadyPressed[code] = false;
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
	}

}
