package nick.dev.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	public enum Buttons {
		Left, Middle, Right
	};
	
	private boolean[] mouseButtons;
	private boolean[] mouseButtonsAlreadyClicked;
	private Integer x, y = 0;
	
	private HashMap<Buttons, Integer> mousebinds;

	public MouseManager() {
		this.mouseButtons = new boolean[MouseInfo.getNumberOfButtons()];
		this.mouseButtonsAlreadyClicked = new boolean[MouseInfo.getNumberOfButtons()];
		
		this.mousebinds = new HashMap<Buttons, Integer>();
		this.mousebinds.put(Buttons.Left, 1);
		this.mousebinds.put(Buttons.Middle, 2);
		this.mousebinds.put(Buttons.Right, 3);
	}

	public void update() {
	}
	
	public boolean mouseIsClicked(Buttons button) {
		Integer pressed = mousebinds.get(button);
		if (mouseButtons[pressed] && !mouseButtonsAlreadyClicked[pressed]) {
			mouseButtonsAlreadyClicked[pressed] = true;
			return true;
		}
		return false;
	}
	
	public boolean mouseIsDown(Buttons button) {
		Integer pressed = mousebinds.get(button);
		if (mouseButtons[pressed]) {
			return true;
		}
		
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/*System.out.println(e.getButton());
		this.mouseButtons[e.getButton()] = true;
		Utilities.Debug("I got clicked!");
		Utilities.Debug(e.getX());
		Utilities.Debug(e.getY());
		x = e.getX();
		y = e.getY();*/
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Integer pressed = e.getButton();
		if (!mouseButtons[pressed] && !mouseButtonsAlreadyClicked[pressed])
			mouseButtons[pressed] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Integer pressed = e.getButton();
		mouseButtons[pressed] = false;
		mouseButtonsAlreadyClicked[pressed] = false;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
