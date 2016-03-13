package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import nick.dev.base.Handler;
import nick.dev.utilities.Utilities;

public class MenuState extends State {

	public Rectangle playButton;
	public Font f;
	public int mainButtonWidth, mainButtonHeight, playButtonX, playButtonY;

	public MenuState(Handler handler) {
		super(handler);
		f = new Font("arial", Font.BOLD, 30);
		mainButtonWidth = handler.getWidth() / 4;
		mainButtonHeight = handler.getHeight() / 8;
		playButtonX = handler.getWidth() / 3;
		playButtonY = handler.getHeight() / 4;
		playButton = new Rectangle(playButtonX, playButtonY, mainButtonWidth, mainButtonHeight);
	}

	@Override
	public void update() {
		if (handler.getMouseManager().leftClick && handler.getMouseManager().getX() != null
				&& handler.getMouseManager().getY() != null) {
			// Utilities.Debug(handler.getMouseManager().getX());
			// Utilities.Debug(handler.getMouseManager().getY());

			// if (handler.getMouseManager().getX() > playButtonX &&
			// handler.getMouseManager().getY() > playButtonY
			// && handler.getMouseManager().getX() < playButtonX +
			// mainButtonWidth
			// && handler.getMouseManager().getY() < playButtonY +
			// mainButtonHeight) {
			// this.exitMenu();
			// }

			if (this.inMouseBounds(playButton, handler.getMouseManager().getX(), handler.getMouseManager().getY())) {
				this.exitMenu();
			}

			handler.getMouseManager().resetXY();
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(f);
		g2d = (Graphics2D) g;
		g2d.draw(playButton);
		g.drawString(Utilities.getPropValue("gameTitle", Utilities.getPropFile()), handler.getWidth() / 3 - 50,
				(handler.getHeight() / 6));
		g.drawString("Play", handler.getWidth() / 3 + 45, (handler.getHeight() / 4) + 50);
		// g.setColor(Color.BLUE);
		// g.fillRect(handler.getMouseManager().getX(),
		// handler.getMouseManager().getY(), 8, 8);
	}

}
