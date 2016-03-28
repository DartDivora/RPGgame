package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nick.dev.base.Handler;
import nick.dev.entities.Player;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;

/**
 * This class will display a menu in the game. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class MenuState extends State {

	private Font f = new Font("arial", Font.BOLD, 25);

	private Integer menuStartX = 0;
	private Integer menuStartY = 0;
	private Integer menuBoxLength = 0;
	private Integer menuBoxHeight = 0;
	// private Integer menuBoxInnerMargin = 10;
	private String[] optionList = new String[6];
	private Integer currentChoice = 0;

	private Integer highlightLength = 0;
	private Integer highlightLengthMax = 100;

	public MenuState(StateManager stateManager) {
		super(stateManager);
		optionList[0] = "Stats";
		optionList[1] = "Spells";
		optionList[2] = "Items";
		optionList[3] = "Bleh";
		optionList[4] = "Blah";
		optionList[5] = "Exit";
	}

	private void reinitialize() {
	}

	/**************************************************************
	 * Update, called every frame.
	 **************************************************************/
	@Override
	public void update() {

		this.menuStartX = Handler.getWidth() / 3;
		this.menuStartY = 1;
		this.menuBoxLength = Handler.getWidth();
		this.menuBoxHeight = Handler.getHeight();

		if (Handler.getKeyManager().keyIsPressed(Keys.ArrowDown)) {
			this.currentChoice = Math.abs((this.currentChoice + 1) % this.optionList.length);
			this.highlightLength = 0;
		} else if (Handler.getKeyManager().keyIsPressed(Keys.ArrowUp)) {
			if (this.currentChoice - 1 != -1) {
				this.currentChoice = Math.abs((this.currentChoice - 1) % this.optionList.length);
				this.highlightLength = 0;
			} else {
				this.currentChoice = (optionList.length - 1);
			}
		}

		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {

			// We may want to improve how this is created later...
			switch (optionList[currentChoice]) {
			case "Stats":
				Player.getStats().printStats();
				break;
			case "Items":
				System.out.println(Player.getInventory().useConsumable("0"));
				Player.getInventory().printBagItems();
				break;
			case "Exit":
				System.exit(0);
				break;
			default:
				System.out.println(optionList[currentChoice] + "!!!");
			}
		}

		if (this.highlightLength <= this.highlightLengthMax) {
			this.highlightLength = Math.min(this.highlightLength + 12, this.highlightLengthMax);
		}

		if (Handler.getKeyManager().keyIsPressed(Keys.Menu)) {
			this.stateManager.changeState(Types.Overworld);
		}

	}

	/**************************************************************
	 * Renders the menu to the screen.
	 **************************************************************/
	@Override
	public void render(Graphics g) {
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.fillRect(this.menuStartX, this.menuStartY - 5, this.menuBoxLength, 5);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.menuStartX, this.menuStartY, this.menuBoxLength, this.menuBoxHeight);

		g.setColor(Color.WHITE);
		for (int i = 0; i < this.optionList.length; ++i) {

			// xPos = middle of the screen, yPos = the middle and then in a
			// list.
			int xPos = Handler.getWidth() / 2 - g.getFontMetrics().stringWidth(this.optionList[0]) / 2;
			int yPos = Handler.getHeight() / 2 + (g.getFontMetrics().getHeight() + 10) * i;

			if (this.currentChoice.equals(i)) {
				int fingerPosX = xPos - 64;
				int fingerPosY = yPos - 30;
				g.drawImage(Assets.finger, fingerPosX, fingerPosY, 50, 50, null);

				g.setColor(new Color(240, 240, 240));

				this.highlightLengthMax = g.getFontMetrics().stringWidth(Utilities.getLongestString(this.optionList));

				g.fillRect(xPos - 5, fingerPosY, this.highlightLength + 10, 40);
			}

			g.setColor(Color.WHITE);
			g.drawString(this.optionList[i], xPos, yPos);
		}
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
