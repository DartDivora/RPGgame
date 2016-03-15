package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nick.dev.base.Handler;
import nick.dev.dialog.DialogManager;
import nick.dev.input.KeyManager.Keys;

/**
 * This class will display dialog in the game. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class DialogState extends State {

	Font f;
	String dialog;
	DialogManager dialogManager;

	public DialogState(StateManager stateManager) {
		super(stateManager);
		f = new Font("arial", Font.BOLD, 25);
		// TODO discuss implementing the DialogManager.
	}

	@Override
	public void update() {
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			this.stateManager.changeState(Types.Overworld);
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO get the renderer methods from clearing the screen....
		g.setFont(f);
		g.setColor(Color.black);
		g.fillRect(0, Handler.getHeight() / 2, Handler.getWidth(), Handler.getHeight() / 2);
		g.setColor(Color.white);
		g.drawString("Text will go here!", 0, Handler.getHeight() / 2 + (Handler.getHeight() / 8));
	}

	@Override
	public void onEnter() {
	}

	@Override
	public void onExit() {
	}

}
