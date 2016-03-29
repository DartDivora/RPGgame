package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nick.dev.base.Handler;
import nick.dev.combat.Spell;
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
	private String subMenu = null;

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
		Boolean talk = Handler.getKeyManager().keyIsPressed(Keys.Talk);

		if (this.subMenu == null) {
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
		} else if (subMenu.equals("Spells")) {
			if (Handler.getKeyManager().keyIsPressed(Keys.ArrowDown)) {
				this.currentChoice = Math.abs((this.currentChoice + 1) % Spell.spellList.length);
				this.highlightLength = 0;
			} else if (Handler.getKeyManager().keyIsPressed(Keys.ArrowUp)) {
				if (this.currentChoice - 1 != -1) {
					this.currentChoice = Math.abs((this.currentChoice - 1) % Spell.spellList.length);
					this.highlightLength = 0;
				} else {
					this.currentChoice = (Spell.spellList.length - 1);
				}
			}
		}

		if (talk && this.subMenu == null) {
			// We may want to improve how this is created later...
			switch (optionList[currentChoice]) {
			case "Stats":
				Player.getStats().printStats();
				break;
			case "Items":
				System.out.println(Player.getInventory().useConsumable("0"));
				Player.getInventory().printBagItems();
				break;
			case "Spells":
				System.out.println("Spells!!!");
				this.subMenu = "Spells";
				break;
			case "Exit":
				System.exit(0);
				break;
			default:
				System.out.println(optionList[currentChoice] + "!!!");
			}
		} else if (talk && this.subMenu.equals("Spells")) {
			System.out.println(Spell.getSpellMap().entrySet());
			switch (Spell.spellList[currentChoice]) {
			case "heal":
				Spell.getSpellMap().get("2").heal(Player.getStats(), Player.getStats());
				this.subMenu = null;
				this.currentChoice = 0;
				break;
			case "warp":
				Spell.getSpellMap().get("3").warp(Player.getStats(), Player.getStats());
				System.out.println("This will warp!");
				this.subMenu = null;
				this.currentChoice = 0;
				break;
			default:
				System.out.println(optionList[currentChoice] + " is not a field spell!!!");
				this.subMenu = null;
				this.currentChoice = 0;
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

		// Figure out how to refactor this into one line...
		if (this.subMenu == null) {
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

					this.highlightLengthMax = g.getFontMetrics()
							.stringWidth(Utilities.getLongestString(this.optionList));

					g.fillRect(xPos - 5, fingerPosY, this.highlightLength + 10, 40);
				}

				g.setColor(Color.WHITE);
				g.drawString(this.optionList[i], xPos, yPos);
			}
		} else if (this.subMenu.equals("Spells")) {
			for (int i = 0; i < Spell.spellList.length; ++i) {
				// xPos = middle of the screen, yPos = the middle and then in a
				// list.
				int xPos = Handler.getWidth() / 2 - g.getFontMetrics().stringWidth(Spell.spellList[0]) / 2;
				int yPos = Handler.getHeight() / 2 + (g.getFontMetrics().getHeight() + 10) * i;

				if (this.currentChoice.equals(i)) {
					int fingerPosX = xPos - 64;
					int fingerPosY = yPos - 30;
					g.drawImage(Assets.finger, fingerPosX, fingerPosY, 50, 50, null);

					g.setColor(new Color(240, 240, 240));

					this.highlightLengthMax = g.getFontMetrics()
							.stringWidth(Utilities.getLongestString(Spell.spellList));

					g.fillRect(xPos - 5, fingerPosY, this.highlightLength + 10, 40);
				}

				g.setColor(Color.WHITE);
				g.drawString(Spell.spellList[i], xPos, yPos);

			}
		}
	}

	// this.drawString

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
