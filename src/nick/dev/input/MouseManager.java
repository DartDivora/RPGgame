package nick.dev.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	// List of possible mouse buttons we can check for.
	// Add new ones as needed, but make sure to initialize them
	// in the constructor.
	public enum Buttons {
		Left, Middle, Right
	};
	
	// Arrays that keep track of the mouse buttons and which
	// have been clicked and checked for. 
	private boolean[] mouseButtons;
	private boolean[] mouseButtonsAlreadyClicked;
	
	// Current x and y positions of the mouse. Should never be null,
	// so maybe we don't need that check. Probably not important.
	private Integer x, y;
	
	// Map of all Buttons to their corresponding button code.
	// Lets us check based on name.
	private HashMap<Buttons, Integer> mousebinds;

	// Constructor. Initializes containers and registers the mousebinds.
	public MouseManager() {
		this.mouseButtons = new boolean[MouseInfo.getNumberOfButtons()];
		this.mouseButtonsAlreadyClicked = new boolean[MouseInfo.getNumberOfButtons()];
		
		this.mousebinds = new HashMap<Buttons, Integer>();
		this.mousebinds.put(Buttons.Left, 1);
		this.mousebinds.put(Buttons.Middle, 2);
		this.mousebinds.put(Buttons.Right, 3);
	}
	
	// Don't currently need this, but we'll keep it just in case.
	public void update() {
	}
	
	// mouseIsClicked returns whether or not a mouse button is pressed - 
	// only returns true once, so do not use for repeating input.
	public boolean mouseIsClicked(Buttons button) {
		Integer pressed = mousebinds.get(button);
		if (mouseButtons[pressed] && !mouseButtonsAlreadyClicked[pressed]) {
			mouseButtonsAlreadyClicked[pressed] = true;
			return true;
		}
		return false;
	}
	
	// mouseIsDown returns whether or not a mouse button is currently
	// being held down.
	public boolean mouseIsDown(Buttons button) {
		Integer pressed = mousebinds.get(button);
		if (mouseButtons[pressed]) {
			return true;
		}
		
		return false;
	}

	// Called when a mouse button is clicked. Already implementing this
	// functionality with mousepressed and mousereleased. Can use this
	// instead if we need to, but for now, what we have works.
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	// Returns the current x position of the mouse.
	public Integer getX() {
		return x;
	}

	// Returns the current y position of the mouse.
	public Integer getY() {
		return y;
	}

	// Called when the mouse is pressed. 
	@Override
	public void mousePressed(MouseEvent e) {
		Integer pressed = e.getButton();
		if (!mouseButtons[pressed] && !mouseButtonsAlreadyClicked[pressed])
			mouseButtons[pressed] = true;
	}

	// Called when the mouse is released. 
	@Override
	public void mouseReleased(MouseEvent e) {
		Integer pressed = e.getButton();
		mouseButtons[pressed] = false;
		mouseButtonsAlreadyClicked[pressed] = false;
	}
	
	// Called when the mouse is moved.
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	
	// Not used, called when mouse enters the frame.
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	// Not used, called when mouse exits the frame.
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	// Not used, called when mouse is held and moving? 
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
