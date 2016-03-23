package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nick.dev.base.Handler;

public class GameOverState extends State {

	public GameOverState(StateManager stateManager) {
		super(stateManager);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Graphics g) {
		
		g.setFont(new Font("ariel", Font.BOLD, 25));
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Handler.getWidth(), Handler.getHeight());
		
		g.setColor(Color.WHITE);
		g.drawString("u ded", Handler.getWidth() / 2 - g.getFontMetrics().stringWidth("u ded") / 2, Handler.getHeight() / 2);
	}
	
	@Override
	public void onEnter() {
		
	}
	
	@Override
	public void onExit() {
		
	}

}
