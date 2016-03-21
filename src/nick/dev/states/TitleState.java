package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.gfx.Assets;
import nick.dev.gfx.SpriteSheet;
import nick.dev.gfx.loadImage;
import nick.dev.input.KeyManager.Keys;
import nick.dev.maps.Map;
import nick.dev.utilities.Utilities;

/**
 * This class will handle the menu system. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class TitleState extends State {

	//private Rectangle playButton;
	private String[] optionList = new String[2];
	private Runnable[] optionActions = new Runnable[2];
	private Integer currentChoice = 0;
	
	private Integer highlightLength = 0;
	private Integer highlightLengthMax = 100;
	public Font f;

	public TitleState(StateManager stateManager) {
		super(stateManager);
		f = new Font("arial", Font.BOLD, 30);
		
		this.optionList[0] = "Start Game";
		this.optionActions[0] = (() -> this.stateManager.changeState(State.Types.Overworld));
		this.optionList[1] = "Quit";
		this.optionActions[1] = (() -> System.exit(0));

	}

	@Override
	public void update() {

//		boolean leftClicked = Handler.getMouseManager().mouseIsClicked(Buttons.Left);
//		if (leftClicked) {
//			Integer mouseX = Handler.getMouseManager().getX();
//			Integer mouseY = Handler.getMouseManager().getY();
//
//			if (Utilities.rectangleContainsPoint(playButton, mouseX, mouseY)) {
//				this.stateManager.changeState(State.Types.Overworld);
//			}
//		}
		
		if (Handler.getKeyManager().keyIsPressed(Keys.ArrowDown)) {
			
			this.currentChoice = Math.abs((this.currentChoice + 1) % this.optionList.length);
			this.highlightLength = 0;
			Handler.getAudioManager().playSFX(Tracks.MenuChangeSFX);
			
		} else if (Handler.getKeyManager().keyIsPressed(Keys.ArrowUp)) {
			
			this.currentChoice = Math.abs((this.currentChoice - 1) % this.optionList.length);
			this.highlightLength = 0;
			Handler.getAudioManager().playSFX(Tracks.MenuChangeSFX);
		}
		
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			this.optionActions[this.currentChoice].run();
		}
		
		if (this.highlightLength <= this.highlightLengthMax) {
			this.highlightLength = Math.min(this.highlightLength + 12, this.highlightLengthMax);
		}
		
		
	}

	@Override
	public void render(Graphics g) {
		g.setFont(f);
		
		// Clear Screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Handler.getWidth(), Handler.getHeight());
		
		for (int i = 0; i < this.optionList.length; ++i) {
			
			// xPos = middle of the screen, yPos = the middle and then in a list.
			int xPos = Handler.getWidth()/2 - g.getFontMetrics().stringWidth(this.optionList[0])/2;
			int yPos = Handler.getHeight()/2 + (g.getFontMetrics().getHeight()+10)*i;
			
			if (this.currentChoice.equals(i)) {
				int fingerPosX = xPos - 64;
				int fingerPosY = yPos - 30;
				g.drawImage(Assets.finger, fingerPosX, fingerPosY, 50, 50, null);
				
				g.setColor(new Color(240, 240, 240));
				
				this.highlightLengthMax = g.getFontMetrics().stringWidth(this.optionList[0]);
				
				g.fillRect(xPos - 5, fingerPosY, this.highlightLength + 10, 40);
			}
			
			g.setColor(Color.DARK_GRAY);
			g.drawString(this.optionList[i], xPos, yPos);
		}
		
		String title = Utilities.getPropValue("gameTitle");
		int xPos = Handler.getWidth()/2 - g.getFontMetrics().stringWidth(title)/2;
		int yPos = Handler.getHeight()/6;
		g.drawString(title, xPos, yPos);
		
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.draw(playButton);
	}

}
