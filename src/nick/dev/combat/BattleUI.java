package nick.dev.combat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;

public class BattleUI {
	
	// Stats
	private Stats[] stats;
	
	private Font f = new Font("ariel", Font.BOLD, 25);
	/*************************************
	 * Main battle area variables
	 *************************************/
	private int menuPadding = 10;
	
	private int mainWindowX = menuPadding / 2;
	private int mainWindowY = Handler.getHeight() / 8;
	private int mainWindowWidth = Handler.getWidth() - menuPadding;
	private int mainWindowHeight = Handler.getHeight() / 2;

	private int spriteWidth = 128;
	private int spriteHeight = 128;
	
	private int playerHealthBarX = mainWindowX + menuPadding;
	private int enemyHealthBarX = mainWindowWidth - spriteHeight - menuPadding;
	private int healthBarY = mainWindowY + spriteHeight + menuPadding;
	private int healthBarWidth = 128;
	private int healthBarHeight = 20;
	
	/*************************************
	 * Menu variables (move to own class?)
	 *************************************/
	private int menuX = 0;
	private int menuY = mainWindowY + mainWindowHeight + menuPadding / 2;
	private int menuWidth = 200;
	private int menuHeight = Handler.getHeight() - mainWindowHeight - mainWindowY - menuPadding;
	
	private String[] optionList = new String[3];
	private Integer currentChoice = 0;
	private Integer highlightLength = 0;
	private Integer highlightLengthMax = 100;
	private HashMap<Integer, String> actions = new HashMap<Integer, String>();

	/*************************************
	 * Log variables
	 *************************************/
	private int logX = menuX + menuWidth + menuPadding / 2;
	private int logY = menuY;
	private int logWidth = Handler.getWidth() - logX - menuPadding;
	private int logHeight = menuHeight;
	
	
	/*************************************
	 * Animations
	 *************************************/
	Animation playerAnim = new Animation(30, Assets.player_down);
	Animation enemyAnim = new Animation(30, Assets.DEFAULT_ANIM_DOWN);

	/*************************************
	 * Constructor, must be passed stats
	 *************************************/
	public BattleUI(Stats[] stats) {
		this.stats = stats;
		
		this.optionList[0] = "Attack";
		this.optionList[1] = "Defend";
		this.optionList[2] = "Spell";
		
		actions.put(0, "Attack");
		actions.put(1, "Defend");
		actions.put(2, "Spell");
	}
	
	/*************************************
	 * Update animations and check for input
	 *************************************/
	public void update() {
		playerAnim.update();
		enemyAnim.update();
		
		this.updateMenu();
	}
	
	private void updateMenu() {
		if (Handler.getKeyManager().keyIsPressed(Keys.ArrowDown)) {
			this.currentChoice = Math.abs((this.currentChoice + 1) % this.optionList.length);
			this.highlightLength = 0;
			
		} else if (Handler.getKeyManager().keyIsPressed(Keys.ArrowUp)) {
			if (this.currentChoice - 1 != -1) {
				this.currentChoice = Math.abs((this.currentChoice - 1) % this.optionList.length);
			} else {
				this.currentChoice = (optionList.length - 1);
			}
			this.highlightLength = 0;
		}

		if (this.highlightLength <= this.highlightLengthMax) {
			this.highlightLength = Math.min(this.highlightLength + 12, this.highlightLengthMax);
		}
	}
	
	/*************************************
	 * Render everything!
	 *************************************/
	public void render(Graphics g) {
		g.setFont(f);
		
		// Draw main box
		g.setColor(Color.WHITE);
		g.fillRect(mainWindowX - menuPadding / 2, mainWindowY - menuPadding / 2, mainWindowWidth + menuPadding, mainWindowHeight + menuPadding);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(mainWindowX, mainWindowY, mainWindowWidth, mainWindowHeight);
		
		// Draw entities
		playerAnim.render(g, mainWindowX + menuPadding, mainWindowY + menuPadding, spriteWidth, spriteHeight);
		enemyAnim.render(g, mainWindowWidth - spriteWidth - menuPadding, mainWindowY + menuPadding, spriteWidth, spriteHeight);
		
		this.renderHealthBars(g);
		this.renderMenu(g);
		this.renderLog(g);
	}
	
	/*************************************
	 * Renders the health bars for the player
	 * and the enemy.
	 *************************************/
	private void renderHealthBars(Graphics g) {
		// Draw player health bar.
		g.setColor(Color.RED);
		g.fillRect(playerHealthBarX, healthBarY, healthBarWidth, healthBarHeight);
		
		int filledPercent = stats[0].getCurrentHP() / stats[0].getMaxHP();
		g.setColor(Color.GREEN);
		g.fillRect(playerHealthBarX, healthBarY, healthBarWidth * filledPercent, healthBarHeight);
		
		// Draw enemy health bar.
		g.setColor(Color.RED);
		g.fillRect(enemyHealthBarX, healthBarY, healthBarWidth, healthBarHeight);

		filledPercent = stats[0].getCurrentHP() / stats[0].getMaxHP();
		g.setColor(Color.GREEN);
		g.fillRect(enemyHealthBarX, healthBarY, healthBarWidth * filledPercent, healthBarHeight);
	}
	
	/*************************************
	 * Renders the menu
	 *************************************/
	private void renderMenu(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(menuX, menuY - menuPadding / 2, menuWidth + menuPadding, menuHeight + menuPadding);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(menuX + menuPadding / 2, menuY, menuWidth, menuHeight);
		
		
		for (int i = 0; i < this.optionList.length; ++i) {

			int xPos = Handler.getWidth() / 2 - g.getFontMetrics().stringWidth(this.optionList[0]) / 2;
			int yPos = (Handler.getHeight() - 100) + (g.getFontMetrics().getHeight() + 10) * i;

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
	}
	
	private void renderLog(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(logX, logY - menuPadding / 2, logWidth + menuPadding, logHeight + menuPadding);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(logX + menuPadding / 2, logY, logWidth, logHeight);
	}
}
