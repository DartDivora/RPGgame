package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;

public class MenuState extends State {

	private Font f = new Font("arial", Font.BOLD, 25);

	private Integer menuStartX = 0;
	private Integer menuStartY = 0;
	private Integer menuBoxLength = 0;
	private Integer menuBoxHeight = 0;
	// private Integer menuBoxInnerMargin = 10;
	// private String[] optionList = new String[3];

	public MenuState(StateManager stateManager) {
		super(stateManager);
	}

	/**************************************************************
	 * Called when entering the state to reset the dialog box.
	 **************************************************************/
	private void reinitialize() {
	}

	/**************************************************************
	 * Update, called every frame. Displays the message.
	 **************************************************************/
	@Override
	public void update() {

		this.menuStartX = 0;
		this.menuStartY = Handler.getHeight() * 2 / 3 + 1;
		this.menuBoxLength = Handler.getWidth();
		this.menuBoxHeight = Handler.getHeight() / 3;

		if (Handler.getKeyManager().keyIsPressed(Keys.Menu)) {
			this.stateManager.changeState(Types.Overworld);
		}

	}

	/**************************************************************
	 * Renders the dialog to the screen.
	 **************************************************************/
	@Override
	public void render(Graphics g) {
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.fillRect(this.menuStartX, this.menuStartY - 5, this.menuBoxLength, 5);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.menuStartX, this.menuStartY, this.menuBoxLength, this.menuBoxHeight);

		g.setColor(Color.WHITE);
		// this.drawString
	}

	/**************************************************************
	 * Called when the state is entered with no arguments, but that doesn't make
	 * sense.
	 **************************************************************/
	@Override
	public void onEnter() {
		this.reinitialize();
	}

	/**************************************************************
	 * Called when the state is entered with a StateArgument
	 **************************************************************/
	@Override
	public void onEnter(StateArgument arg) {
	}

	/**************************************************************
	 * Called when the state is leaving.
	 **************************************************************/
	@Override
	public void onExit() {
	}

}
