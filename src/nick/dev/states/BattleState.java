package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.combat.Stats;
import nick.dev.entities.Monster;
import nick.dev.entities.Player;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;

/**
 * This class will handle the battle system. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class BattleState extends State {

	public BattleState(StateManager stateManager) {
		super(stateManager);
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public void onEnter() {
		
	}
	
	@Override
	public void onExit() {
		
	}
	
}
	
	
	
	/*private String[] combatants = new String[2];
	private Stats[] combatStats = new Stats[2];
	private boolean[] defending = new boolean[2];

	// Later, combat will be handled by a BattleManager or something like that.
	
	int healthBarWidth, healthBarHeight, creatureDisplayWidth, creatureDisplayHeight;
	
	String playerAction = null;
	String[] turnResults = null;
	private Integer currentChoice = 0;
	private String[] optionList = new String[3];
	private HashMap<Integer, String> actions;
	private Integer highlightLength = 0;
	private Integer highlightLengthMax = 100;
	private Boolean battleText = false;
	
	Animation playerAnim = new Animation(30, Assets.player_down);
	Animation enemyAnim = new Animation(30, Assets.DEFAULT_ANIM_DOWN);

	Font f;
	
	public BattleState(StateManager stateManager) {
		super(stateManager);
		
		combatants[0] = "Player";
		combatants[1] = "Gnoll";
		
		combatStats[0] = Player.getStats();
		combatStats[1] = Monster.getStats("gnoll");
		
		actions = new HashMap<Integer, String>();
		actions.put(0, "Attack");
		actions.put(1, "Defend");
		actions.put(2, "Spell");

		f = new Font("arial", Font.BOLD, 25);
		healthBarWidth = Integer.parseInt(Utilities.getPropValue("healthBarWidth"));
		healthBarHeight = Integer.parseInt(Utilities.getPropValue("healthBarHeight"));
		creatureDisplayWidth = Integer.parseInt(Utilities.getPropValue("creatureDisplayWidth"));
		creatureDisplayHeight = Integer.parseInt(Utilities.getPropValue("creatureDisplayHeight"));

		this.optionList[0] = "Attack";
		this.optionList[1] = "Defend";
		this.optionList[2] = "Spell";
	}
	
	public void update() {
		
		playerAnim.update();
		enemyAnim.update();
		
		playerAction = null;
		this.defending[0] = false;
		this.defending[1] = false;
		
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

		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			playerAction = actions.get(this.currentChoice);
		}

		if (this.highlightLength <= this.highlightLengthMax) {
			this.highlightLength = Math.min(this.highlightLength + 12, this.highlightLengthMax);
		}

		if (playerAction != null) {
			if (combatStats[0].getCurrentHP() > 0 && combatStats[1].getCurrentHP() > 0) {
				this.doAction(playerAction, this.getRandomAction());

				if (combatStats[0].getCurrentHP() <= 0) {
					Utilities.Debug(("Game over, man!"));
					this.gameOver();
				}
				if (combatStats[1].getCurrentHP() <= 0) {
					Utilities.Debug("You won!!!");
					this.leaveBattle();
				}
			}
		}

		if (!battleText) {
			this.battleText = true;
			StateArgument arg = new StateArgument();
			arg.setDialogLine(1);
			this.stateManager.changeState(Types.Dialog, arg);
			Handler.getAudioManager().playTrack(Tracks.Battle);
		}
	}
	
	public String[] doAction(String playerAction, String enemyAction) {
		turnResults = new String[2];
		if (playerAction.equals("Defend")) {
			turnResults[0] = Defend(0);
		}
		if (enemyAction.equals("Defend")) {
			turnResults[1] = Defend(1);
		}
		if (playerAction.equals("Attack")) {
			turnResults[0] = Attack(0, 1);
		}
		if (enemyAction.equals("Attack")) {
			turnResults[1] = Attack(1, 0);
		}
		if (playerAction.equals("Spell")) {
			turnResults[0] = Spell(0, 1);
		}
		if (enemyAction.equals("Spell")) {
			turnResults[1] = Spell(1, 0);
		}
		return turnResults;
	}
	
	public String Attack(Integer attacker, Integer defender) {
		String attackMessage = null;
		
		Stats attStats = combatStats[attacker];
		Stats defStats = combatStats[defender];
		
		
		Integer defenderDefense = defStats.getDefense();
		if (defending[defender]) {
			defenderDefense = defenderDefense * 2;
			Utilities.Debug("Defending! Doubling defense to " + defenderDefense);
			defending[defender] = false;
		}
		Integer enemyAttack = Utilities.getRandomNumber(0, attStats.getStrength()) - defenderDefense;
		if (enemyAttack > 0) {
			attackMessage = combatants[defender] + " takes:" + enemyAttack + " damage!";
			Utilities.Debug(attackMessage);
			defStats.setCurrentHP(defStats.getCurrentHP() - enemyAttack);
		} else {
			attackMessage = combatants[attacker] + " missed!";
			Utilities.Debug(combatants[attacker] + " missed!");
		}
		Utilities.Debug(attackMessage);
		return attackMessage;
	}

	public String Spell(Integer attacker, Integer defender) {
		String attackMessage = null;
		
		Stats attStats = combatStats[attacker];
		Stats defStats = combatStats[defender];
		
		Integer defenderDefense = defStats.getDefense();
		if (defending[defender]) {
			defenderDefense = defenderDefense * 2;
			Utilities.Debug("Defending! Doubling defense to " + defenderDefense);
			defending[defender] = false;
		}
		Integer enemyAttack = Utilities.getRandomNumber(0, attStats.getIntelligence()) - defenderDefense;
		if (enemyAttack > 0) {
			attackMessage = combatants[defender] + " takes:" + enemyAttack + " damage!";
			Utilities.Debug(attackMessage);
			defStats.setHP(defStats.getHP() - enemyAttack);
		} else {
			attackMessage = combatants[attacker] + " missed!";
			Utilities.Debug(combatants[attacker] + " missed!");
		}
		Utilities.Debug(attackMessage);
		return attackMessage;
	}

	public String Defend(Integer combatantIndex) {
		String defendMessage = null;
//		defendMessage = c.getEntityName() + " is defending!";
//		Utilities.Debug(defendMessage);
		this.defending[combatantIndex] = true;
		return defendMessage;
	}
	
	@Override
	public void render(Graphics g) {
		//g.setColor(Color.WHITE);
		//g.fillRect(0, Handler.getHeight() / 4 - 5, Handler.getWidth(), Handler.getHeight() / 2 + 10);
		
		//g.setColor(Color.DARK_GRAY);
		//g.fillRect(0, Handler.getHeight() / 4, Handler.getWidth(), Handler.getHeight() / 2);
		
		g.setFont(f);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Handler.getWidth(), Handler.getHeight());
		
		playerAnim.render(g, 0, 0);
		enemyAnim.render(g, 300, 100);
		
//		g.drawImage(Handler.getWorld().getEntityManager().getPlayer().getAnimDown().getCurrentFrame(),
//				Handler.getWidth() / 6, Handler.getHeight() / 3, creatureDisplayWidth, creatureDisplayHeight, null);
//		g.drawImage(creature.getAnimDown().getCurrentFrame(), Handler.getWidth() / 2, Handler.getHeight() / 3,
//				creatureDisplayWidth, creatureDisplayHeight, null);
		g.drawString("This is a battle?", Handler.getWidth() / 3, (Handler.getHeight() / 6));
		g.setColor(java.awt.Color.red);
		g.fillRect(Handler.getWidth() / 6, Handler.getHeight() / 3 + 150, healthBarWidth, healthBarHeight);
		g.fillRect(Handler.getWidth() / 2, Handler.getHeight() / 3 + 150, healthBarWidth, healthBarHeight);
		g.setColor(java.awt.Color.green);
		g.fillRect(Handler.getWidth() / 6, Handler.getHeight() / 3 + 150,
				(int) (((double) combatStats[0].getCurrentHP() / (double) combatStats[0].getHP())
						* healthBarWidth),
				healthBarHeight);
		g.fillRect(Handler.getWidth() / 2, Handler.getHeight() / 3 + 150,
				(int) (((double) combatStats[1].getCurrentHP() / (double) combatStats[1].getHP()) * healthBarWidth),
				healthBarHeight);

		g.setColor(java.awt.Color.BLACK);

		if (turnResults != null) {
			g.setFont(f);
			for (int i = 0; i < turnResults.length; i++) {
				if (turnResults[i] != null) {
					g.drawString(turnResults[i], 300, 500 + ((i - 1) * 50));
				}
			}
		}

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
	
	public String getRandomAction() {
		Integer randNumber = Utilities.getRandomNumber(1, actions.size());
		Utilities.Debug(randNumber - 1);
		Utilities.Debug(actions.get(randNumber - 1));
		return actions.get(randNumber - 1);
	}
	
	public void gameOver() {
		Utilities.Debug("Game Over! Your health reached 0...");
		Handler.getGame().stop();
	}
	
	public void leaveBattle() {
		Utilities.Debug("Leaving battle!");
		this.stateManager.leaveState();
		Handler.getAudioManager().stopCurrentTrack();
		Handler.getAudioManager().playTrack(Tracks.Overworld);

		//Handler.getPlayer().setCurrentExperience(Handler.getPlayer().getCurrentExperience() + creature.getExpToGive());// DBC
		//Handler.getPlayer().getStats();// DBC
		combatStats[1].setCurrentHP(10); // DBC
	}
	
	public void drawCreatures(Graphics g) {
		
	}
	
	public void drawMenu(Graphics g) {
		
	}
	
	@Override
	public void onEnter(StateArgument arg) {
		
	}
	
	@Override
	public void onExit() {
		
	}
	
	
	
	
	
	
}
	private Gnoll creature;
	
	

	public BattleState(StateManager stateManager) {
		super(stateManager);

		
	}

	@Override
	public void update() {
		// Eventually the player won't carry over to the battle state.
		// We can keep the stats and sprite visible to it, but the player class
		// should only be for the maps, not battles.
		Handler.getWorld().getEntityManager().getPlayer().update();
		creature.update();
		playerAction = null;

		if (Handler.getKeyManager().keyIsPressed(Keys.ArrowDown)) {
			this.currentChoice = Math.abs((this.currentChoice + 1) % this.optionList.length);
			this.highlightLength = 0;
			Handler.getAudioManager().playSFX(Tracks.MenuChangeSFX);
		} else if (Handler.getKeyManager().keyIsPressed(Keys.ArrowUp)) {
			if (this.currentChoice - 1 != -1) {
				this.currentChoice = Math.abs((this.currentChoice - 1) % this.optionList.length);
			} else {
				this.currentChoice = (optionList.length - 1);
			}
			this.highlightLength = 0;
			Handler.getAudioManager().playSFX(Tracks.MenuChangeSFX);
		}

		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			playerAction = actions.get(this.currentChoice);
		}

		if (this.highlightLength <= this.highlightLengthMax) {
			this.highlightLength = Math.min(this.highlightLength + 12, this.highlightLengthMax);
		}

		if (playerAction != null) {
			if (Handler.getPlayer().getCurrentHP() > 0 && creature.getCurrentHP() > 0) {
				this.doAction(playerAction, this.getRandomAction());

				if (Handler.getPlayer().getCurrentHP() <= 0) {
					Utilities.Debug(("Game over, man!"));
					this.gameOver();
				}
				if (creature.getCurrentHP() <= 0) {
					Utilities.Debug("You won!!!");
					this.leaveBattle();
				}
			}
		}

		if (!battleText) {
			this.battleText = true;
			StateArgument arg = new StateArgument();
			arg.setDialogLine(1);
			this.stateManager.changeState(Types.Dialog, arg);
			Handler.getAudioManager().playTrack(Tracks.Battle);
		}
	}

	@Override
	public void onEnter() {
		this.battleText = false;
	}

	@Override
	public void onExit() {
		Handler.getAudioManager().stopCurrentTrack();
	}

	

	

	public void leaveBattle() {
		Utilities.Debug("Leaving battle!");
		this.stateManager.leaveState();
		Handler.getAudioManager().stopCurrentTrack();
		Handler.getAudioManager().playTrack(Tracks.Overworld);
		Handler.getWorld().getEntityManager().getPlayer().setGoToBattle(false);

		Handler.getPlayer().setCurrentExperience(Handler.getPlayer().getCurrentExperience() + creature.getExpToGive());// DBC
		Handler.getPlayer().getStats();// DBC
		creature.setCurrentHP(10); // DBC
	}

	

	

	@Override
	public void render(Graphics g) {
		g.setFont(f);

		// Clear Screen
		g.clearRect(0, 0, Handler.getWidth(), Handler.getHeight());

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Handler.getWidth(), Handler.getHeight());

		g.drawImage(Handler.getWorld().getEntityManager().getPlayer().getAnimDown().getCurrentFrame(),
				Handler.getWidth() / 6, Handler.getHeight() / 3, creatureDisplayWidth, creatureDisplayHeight, null);
		g.drawImage(creature.getAnimDown().getCurrentFrame(), Handler.getWidth() / 2, Handler.getHeight() / 3,
				creatureDisplayWidth, creatureDisplayHeight, null);
		g.drawString("This is a battle?", Handler.getWidth() / 3, (Handler.getHeight() / 6));
		g.setColor(java.awt.Color.red);
		g.fillRect(Handler.getWidth() / 6, Handler.getHeight() / 3 + 150, healthBarWidth, healthBarHeight);
		g.fillRect(Handler.getWidth() / 2, Handler.getHeight() / 3 + 150, healthBarWidth, healthBarHeight);
		g.setColor(java.awt.Color.green);
		g.fillRect(Handler.getWidth() / 6, Handler.getHeight() / 3 + 150,
				(int) (((double) Handler.getPlayer().getCurrentHP() / (double) Handler.getPlayer().getMaxHP())
						* healthBarWidth),
				healthBarHeight);
		g.fillRect(Handler.getWidth() / 2, Handler.getHeight() / 3 + 150,
				(int) (((double) creature.getCurrentHP() / (double) creature.getMaxHP()) * healthBarWidth),
				healthBarHeight);

		g.setColor(java.awt.Color.BLACK);

		if (turnResults != null) {
			g.setFont(f);
			for (int i = 0; i < turnResults.length; i++) {
				if (turnResults[i] != null) {
					g.drawString(turnResults[i], 300, 500 + ((i - 1) * 50));
				}
			}
		}

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
	*/
