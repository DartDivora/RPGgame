package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import nick.dev.base.Handler;
import nick.dev.input.MouseManager.Buttons;
import nick.dev.utilities.Utilities;

public class MenuState extends State {

	private Rectangle playButton;
	public Font f;

	public MenuState(StateManager stateManager) {
		super(stateManager);
		f = new Font("arial", Font.BOLD, 30);
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
	}

	@Override
	public void render(Graphics g) {
		g.setFont(f);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(playButton);
		g.drawString(Utilities.getPropValue("gameTitle", Utilities.getPropFile()), Handler.getWidth() / 3 - 50,
				(Handler.getHeight() / 6));
		g.drawString("Play", Handler.getWidth() / 3 + 45, (Handler.getHeight() / 4) + 50);
		// g.setColor(Color.BLUE);
		// g.fillRect(handler.getMouseManager().getX(),
		// handler.getMouseManager().getY(), 8, 8);
	}

}
