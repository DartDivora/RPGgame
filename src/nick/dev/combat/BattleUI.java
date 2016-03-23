package nick.dev.combat;

import java.awt.Color;
import java.awt.Graphics;

import nick.dev.base.Handler;

public class BattleUI {

	public BattleUI() {
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, Handler.getHeight() / 4 - 5, Handler.getWidth(), Handler.getHeight() / 2 + 10);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, Handler.getHeight() / 4, Handler.getWidth(), Handler.getHeight() / 2);
	}
}
