package nick.dev.combat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**************************************************************************
 * This class handles battles actions.
 * TODO: See about implementing animations, damage numbers, etc.
 * 
 * @author nsanft,acharles
 * @version 1.1
 *************************************************************************/
public class BattleManager {
	
	/*************************************
	 * Action defines all of the different actions
	 * someone can perform.
	 *************************************/
	public enum Action {
		Attack, Defend, Spell;
		
		private static final List<Action> values =
				Collections.unmodifiableList(Arrays.asList(values()));
		private static final int size = values.size();
		private static final Random random = new Random();
		
		public static Action getRandom() {
			// size-1 so that it doesn't choose magic.
			return values.get(random.nextInt(size-1));
		}
	};
	
	private Stats[] stats;
	private boolean[] defending;
	
	/************************************************************
	 * Constructor
	 ************************************************************/
	public BattleManager(Stats[] stats) {
		this.stats = stats;
		defending = new boolean[stats.length];
	}
	
	/************************************************************
	 * Chooses an action for the target and
	 * then executes that and the player's
	 * actions.
	 ************************************************************/
	public String[] doCombatTurn(Action playerChoice, int playerIndex, int targetIndex) {
		
		String[] results = new String[stats.length];
		
		for (int i = 0; i < defending.length; ++i) {
			defending[i] = false;
		}
		
		// Right now, this just checks to see if someone is defending
		// and makes them go first. Later, maybe turn order is decided by
		// speed and defend just buffs their defense the next time they get hit.
		Action targetAction = Action.getRandom();
		if (targetAction == Action.Defend) {
			results[targetIndex] = this.executeAction(targetAction, targetIndex, playerIndex);
			results[playerIndex] = this.executeAction(playerChoice, playerIndex, targetIndex);
		} else {
			results[playerIndex] = this.executeAction(playerChoice, playerIndex, targetIndex);
			results[targetIndex] = this.executeAction(targetAction, targetIndex, playerIndex);
		}
		
		return results;
	}
	
	/************************************************************
	 * Executes one action. This is where the battle logic will go for each action.
	 ************************************************************/
	public String executeAction(Action action, int sourceIndex, int targetIndex) {
		
		switch(action) {
		case Attack:
			Integer targetDefense = stats[targetIndex].getDefense();
			targetDefense = (defending[targetIndex]) ? targetDefense * 2 : targetDefense;
			
			Integer damage = Math.max(0, stats[sourceIndex].getStrength() - targetDefense);
			stats[targetIndex].setCurrentHP(stats[targetIndex].getCurrentHP() - damage);
			
			return stats[sourceIndex].getName() + " did " + damage + " damage to " + stats[targetIndex].getName() + "!";
		case Defend:
			defending[sourceIndex] = true;
			
			return stats[sourceIndex].getName() + " is defending!";			
		case Spell:
			return "You've got no spells, dummy!";
		default:
			return null;
		}
	}
}
