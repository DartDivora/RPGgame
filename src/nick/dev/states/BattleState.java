package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.base.entities.Creature;
import nick.dev.base.entities.Gnoll;
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
	private Gnoll creature;
	int healthBarWidth, healthBarHeight, creatureDisplayWidth, creatureDisplayHeight;
	String playerAction = null;
	String[] turnResults = null;
	private Integer currentChoice = 0;
	private String[] optionList = new String[3];
	private HashMap<Integer, String> actions;
	private Integer highlightLength = 0;
	private Integer highlightLengthMax = 100;
	private Boolean battleText = false;

	Font f;

	public BattleState(StateManager stateManager) {
		super(stateManager);

		actions = new HashMap<Integer, String>();
		actions.put(0, "Attack");
		actions.put(1, "Defend");
		actions.put(2, "Spell");

		f = new Font("arial", Font.BOLD, 25);
		healthBarWidth = Integer.parseInt(Utilities.getPropValue("healthBarWidth"));
		healthBarHeight = Integer.parseInt(Utilities.getPropValue("healthBarHeight"));
		creatureDisplayWidth = Integer.parseInt(Utilities.getPropValue("creatureDisplayWidth"));
		creatureDisplayHeight = Integer.parseInt(Utilities.getPropValue("creatureDisplayHeight"));

		creature = new Gnoll(0, 0, 0, 0);

		this.optionList[0] = "Attack";
		this.optionList[1] = "Defend";
		this.optionList[2] = "Spell";
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

	public String[] doAction(String playerAction, String enemyAction) {
		turnResults = new String[2];
		if (playerAction.equals("Defend")) {
			turnResults[0] = Defend(Handler.getPlayer());
		}
		if (enemyAction.equals("Defend")) {
			turnResults[1] = Defend(creature);
		}
		if (playerAction.equals("Attack")) {
			turnResults[0] = Attack(Handler.getPlayer(), creature);
		}
		if (enemyAction.equals("Attack")) {
			turnResults[1] = Attack(creature, Handler.getPlayer());
		}
		if (playerAction.equals("Spell")) {
			turnResults[0] = Spell(Handler.getPlayer(), creature);
		}
		if (enemyAction.equals("Spell")) {
			turnResults[1] = Spell(creature, Handler.getPlayer());
		}
		return turnResults;
	}

	public String getRandomAction() {
		Integer randNumber = Utilities.getRandomNumber(1, actions.size());
		Utilities.Debug(randNumber - 1);
		Utilities.Debug(actions.get(randNumber - 1));
		return actions.get(randNumber - 1);
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

	public String Attack(Creature attacker, Creature defender) {
		String attackMessage = null;
		Integer defenderDefense = defender.getDefense();
		if (defender.isDefending()) {
			defenderDefense = defenderDefense * 2;
			Utilities.Debug("Defending! Doubling defense to " + defenderDefense);
			defender.setDefending(false);
		}
		Integer enemyAttack = Utilities.getRandomNumber(0, attacker.getStrength()) - defenderDefense;
		if (enemyAttack > 0) {
			attackMessage = defender.getEntityName() + " takes:" + enemyAttack + " damage!";
			Utilities.Debug(attackMessage);
			defender.setCurrentHP(defender.getCurrentHP() - enemyAttack);
		} else {
			attackMessage = attacker.getEntityName() + " missed!";
			Utilities.Debug(attacker.getEntityName() + " missed!");
		}
		Utilities.Debug(attackMessage);
		return attackMessage;
	}

	public String Spell(Creature attacker, Creature defender) {
		String attackMessage = null;
		Integer defenderDefense = defender.getDefense();
		if (defender.isDefending()) {
			defenderDefense = defenderDefense * 2;
			Utilities.Debug("Defending! Doubling defense to " + defenderDefense);
			defender.setDefending(false);
		}
		Integer enemyAttack = Utilities.getRandomNumber(0, attacker.getIntelligence()) - defenderDefense;
		if (enemyAttack > 0) {
			attackMessage = defender.getEntityName() + " takes:" + enemyAttack + " damage!";
			Utilities.Debug(attackMessage);
			defender.setCurrentHP(defender.getCurrentHP() - enemyAttack);
		} else {
			attackMessage = attacker.getEntityName() + " missed!";
			Utilities.Debug(attacker.getEntityName() + " missed!");
		}
		Utilities.Debug(attackMessage);
		return attackMessage;
	}

	public String Defend(Creature c) {
		String defendMessage = null;
		defendMessage = c.getEntityName() + " is defending!";
		Utilities.Debug(defendMessage);
		c.setDefending(true);
		return defendMessage;
	}

	public void gameOver() {
		Utilities.Debug("Game Over! Your health reached 0...");
		Handler.getGame().stop();
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
}
