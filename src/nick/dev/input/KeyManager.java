package nick.dev.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import nick.dev.utilities.Utilities;

/**
 * This class will manage Key presses. Implements KeyListener.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */

/**
 * List of all the possible keys we can check for. Add as needed, but make sure
 * to initialize it to a code in the constructor.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class KeyManager implements KeyListener {

	public enum Keys {
		Up, Down, Left, Right, Space, Mute, Talk, ArrowDown, ArrowUp
	};

	/**
	 * Map of all the keybinds to their key codes. Used for easier checking
	 * since we can change the names.
	 */

	private HashMap<Keys, Integer> keybinds;

	/**
	 * Arrays of keys and keys that have already been pressed and checked for.
	 * Lets us check for a single press.
	 */

	private boolean[] keys;
	private boolean[] keysAlreadyPressed;

	/**
	 * Constructor. Initializes containers and registers the keybinds.
	 */
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
		keybinds.put(Keys.Talk, KeyEvent.VK_ENTER);
		keybinds.put(Keys.ArrowDown, KeyEvent.VK_DOWN);
		keybinds.put(Keys.ArrowUp, KeyEvent.VK_UP);
	}

	/**
	 * update is called every frame. Currently no need for this, but we'll keep
	 * it just in case we need it later.
	 */
	public void update() {
	}

	/**
	 * keyIsPressed returns whether or not a key is pressed - only returns true
	 * once, so do not use for repeating input.
	 * 
	 * @param key
	 * @return
	 */
	public boolean keyIsPressed(Keys key) {
		int code = keybinds.get(key);
		if (keys[code] && !keysAlreadyPressed[code]) {
			keysAlreadyPressed[code] = true;
			return true;
		}
		return false;
	}

	/**
	 * keyIsDown returns whether or not a key is currently being held down.
	 * 
	 * @param key
	 * @return
	 */
	public boolean keyIsDown(Keys key) {
		int code = keybinds.get(key);
		if (keys[code]) {
			return true;
		}

		return false;
	}

	/**
	 * This event is called when the mouse is pressed.
	 */
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

	/**
	 * This event is called when the mouse is released.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		try {
			int code = e.getKeyCode();
			keys[code] = false;
			keysAlreadyPressed[code] = false;
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
