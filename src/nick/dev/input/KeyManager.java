package nick.dev.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import nick.dev.utilities.Utilities;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	public boolean up, down, left, right, space;

	public KeyManager() {
		keys = new boolean[256];

	}

	public void update() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = true;
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
		Utilities.Debug("Pressed: " + e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = false;
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
	}

}
