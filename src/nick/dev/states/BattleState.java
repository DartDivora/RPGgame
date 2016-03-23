package nick.dev.states;

import java.awt.Graphics;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.combat.BattleManager;
import nick.dev.combat.BattleManager.Action;
import nick.dev.combat.BattleUI;
import nick.dev.combat.Stats;
import nick.dev.entities.Monster;
import nick.dev.entities.Player;
import nick.dev.input.KeyManager.Keys;

/**************************************************************************
 * This class will handle the battle system. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *************************************************************************/
public class BattleState extends State {
	
	private Stats[] stats = new Stats[2];
	
	private BattleUI battleUI;
	private BattleManager battleManager;
	
	/*************************************
	 * Constructor
	 *************************************/
	public BattleState(StateManager stateManager) {
		super(stateManager);
	}
	
	/*************************************
	 * Update the UI and check for actions
	 *************************************/
	@Override
	public void update() {
		
		battleUI.update();
		
		// Get an action from the UI.
		Action action = battleUI.getAction();
		if (action != null) {
			String[] results = battleManager.doCombatTurn(action, 0, 1);
			
			// Check for deaths :(
			if (stats[1].getCurrentHP() <= 0) {
				stats[1].setCurrentHP(stats[1].getMaxHP());
				this.stateManager.leaveState();
			} else if (stats[0].getCurrentHP() <= 0) {
				this.stateManager.changeState(Types.GameOver);
			}
			this.battleUI.putInLog(results);
		}
		
		// You can leave anytime....
		if (Handler.getKeyManager().keyIsPressed(Keys.Space)) {
			this.stateManager.leaveState();
		}
	}
	
	/*************************************
	 * Render everything (just UI right now)
	 *************************************/
	@Override
	public void render(Graphics g) {
		battleUI.render(g);
	}
	
	/*************************************
	 * When we enter the state, we basically
	 * want to reset everything.
	 *************************************/
	@Override
	public void onEnter() {
		Handler.getAudioManager().playTrack(Tracks.Battle);
		
		Stats[] stats = new Stats[2];
		this.stats = stats;
		
		stats[0] = Player.getStats();
		stats[1] = Monster.getRandomStats();
		battleUI = new BattleUI(stats);
		battleManager = new BattleManager(stats);
	}
	
	/*************************************
	 * Nothing on exit yet.
	 *************************************/
	@Override
	public void onExit() {
		Handler.getAudioManager().stopCurrentTrack();
	}
	
}
	
	
	
	/*private String[] combatants = new String[2];
	private Stats[] combatStats = new Stats[2];
	private boolean[] defending = new boolean[2];
	
	public void update() {
		

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
	
	public String Attack(Integer attacker, Integer defender) {
		
		
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
*/