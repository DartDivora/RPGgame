package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import nick.dev.base.Handler;
import nick.dev.utilities.Utilities;

public abstract class State {
	
	public enum Types { Title, Overworld, Localworld, Battle, Menu };
	
	public boolean inMenu = true;
	public Graphics2D g2d;
	public Font f;

	private static State currentState = null;
	private static State returnState = null;

	public static void setState(State state) {
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}

	// Class
	protected Handler handler;

	public State(Handler handler) {
		this.handler = handler;
	}

	public abstract void update();

	public abstract void render(Graphics g);

	public boolean isInMenu() {
		return inMenu;
	}

	public void exitMenu() {
		this.inMenu = false;
	}

	public State getReturnState() {
		return returnState;
	}

	public void setReturnState(State returnState) {
		State.returnState = returnState;
	}

	public boolean inMouseBounds(Rectangle button, int MouseX, int MouseY) {
		Utilities.Debug(button.contains(MouseX, MouseY));
		if (button.contains(MouseX, MouseY)) {
			return true;
		} else {
			return false;
		}
	}

}
