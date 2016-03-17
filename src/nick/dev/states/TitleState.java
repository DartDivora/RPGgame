package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;
import nick.dev.input.MouseManager.Buttons;
import nick.dev.utilities.Utilities;

/**
 * This class will handle the menu system. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class TitleState extends State {

	private Rectangle playButton;
	private String[] optionList = new String[2];
	private Runnable[] optionActions = new Runnable[2];
	private Integer currentChoice = 0;
	public Font f;

	public TitleState(StateManager stateManager) {
		super(stateManager);
		f = new Font("arial", Font.BOLD, 30);
		
		this.optionList[0] = "Start Game";
		this.optionActions[0] = (() -> this.stateManager.changeState(State.Types.Overworld));
		this.optionList[1] = "Quit";
		this.optionActions[1] = (() -> System.exit(0));
		
		Integer mainButtonWidth = Handler.getWidth() / 4;
		Integer mainButtonHeight = Handler.getHeight() / 8;
		Integer playButtonX = Handler.getWidth() / 3;
		Integer playButtonY = Handler.getHeight() / 4;
		playButton = new Rectangle(playButtonX, playButtonY, mainButtonWidth, mainButtonHeight);
	}

	@Override
	public void update() {

		boolean leftClicked = Handler.getMouseManager().mouseIsClicked(Buttons.Left);
		if (leftClicked) {
			Integer mouseX = Handler.getMouseManager().getX();
			Integer mouseY = Handler.getMouseManager().getY();

			if (Utilities.rectangleContainsPoint(playButton, mouseX, mouseY)) {
				this.stateManager.changeState(State.Types.Overworld);
			}
		}
		
		if (Handler.getKeyManager().keyIsPressed(Keys.ArrowDown)) {
			this.currentChoice = Math.abs((this.currentChoice + 1) % this.optionList.length);
		} else if (Handler.getKeyManager().keyIsPressed(Keys.ArrowUp)) {
			this.currentChoice = Math.abs((this.currentChoice - 1) % this.optionList.length);
		}
		
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			this.optionActions[this.currentChoice].run();
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setFont(f);
		// Clear Screen
		g.clearRect(0, 0, Handler.getWidth(), Handler.getHeight());
		
		for (int i = 0; i < this.optionList.length; ++i) {
			int xPos = Handler.getWidth()/2 - g.getFontMetrics().stringWidth(this.optionList[0])/2;
			int yPos = Handler.getHeight()/2 + (g.getFontMetrics().getHeight()+10)*i;
			g.drawString(this.optionList[i], xPos, yPos);
			
			if (this.currentChoice.equals(i)) {
				g.drawString(">", xPos - 30, yPos);
			}
		}
		
		String title = Utilities.getPropValue("gameTitle");
		int xPos = Handler.getWidth()/2 - g.getFontMetrics().stringWidth(title)/2;
		int yPos = Handler.getHeight()/6;
		g.drawString(title, xPos, yPos);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(playButton);
	}

}
