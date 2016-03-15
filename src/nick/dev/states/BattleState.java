package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.base.entities.Creature;
import nick.dev.base.entities.Gnoll;
import nick.dev.input.MouseManager.Buttons;
import nick.dev.utilities.Utilities;

/**
 * This class will handle the battle system. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class BattleState extends State {
	private Gnoll creature;
	private String[] actions;
	public Rectangle attackButton, defendButton;
	int healthBarWidth, healthBarHeight, creatureDisplayWidth, creatureDisplayHeight;
	String playerAction = null;
	String[] turnResults = null;
	
	Font f;

	public BattleState(StateManager stateManager) {
		super(stateManager);

		actions = new String[2];
		actions[0] = "Attack";
		actions[1] = "Defend";

		f = new Font("arial", Font.BOLD, 25);
		healthBarWidth = Integer.parseInt(Utilities.getPropValue("healthBarWidth", Utilities.getPropFile()));
		healthBarHeight = Integer.parseInt(Utilities.getPropValue("healthBarHeight", Utilities.getPropFile()));
		creatureDisplayWidth = Integer
				.parseInt(Utilities.getPropValue("creatureDisplayWidth", Utilities.getPropFile()));
		creatureDisplayHeight = Integer
				.parseInt(Utilities.getPropValue("creatureDisplayHeight", Utilities.getPropFile()));

		Integer mainButtonWidth = Handler.getWidth() / 4;
		Integer mainButtonHeight = Handler.getHeight() / 5;
		Integer attackButtonX = 0;
		Integer attackButtonY = Handler.getHeight() - (Handler.getHeight() / 4);
		Integer defendButtonX = mainButtonWidth;
		Integer defendButtonY = attackButtonY;

		attackButton = new Rectangle(attackButtonX, attackButtonY, mainButtonWidth, mainButtonHeight);
		defendButton = new Rectangle(defendButtonX, defendButtonY, mainButtonWidth, mainButtonHeight);

		creature = new Gnoll(0, 0, 0, 0);
	}

	@Override
	public void update() {
		// Eventually the player won't carry over to the battle state.
		// We can keep the stats and sprite visible to it, but the player class
		// should only be for the maps, not battles.
		Handler.getWorld().getEntityManager().getPlayer().update();
		creature.update();
		playerAction = null;
		
		Integer mouseX = Handler.getMouseManager().getX();
		Integer mouseY = Handler.getMouseManager().getY();
		boolean leftClicked = Handler.getMouseManager().mouseIsClicked(Buttons.Left);
		
		if (leftClicked && (mouseX != null && mouseY != null)) {
			if (Utilities.rectangleContainsPoint(attackButton, mouseX, mouseY)) {
				playerAction = "Attack";
			} else if (Utilities.rectangleContainsPoint(defendButton, mouseX, mouseY)) {
				playerAction = "Defend";
			}

			if (playerAction != null) {
				if (Handler.getPlayer().getCurrentHealth() > 0 && creature.getCurrentHealth() > 0) {
					this.doAction(playerAction, this.getRandomAction());

					if (Handler.getPlayer().getCurrentHealth() <= 0) {
						System.out.println("Game over, man!");
						this.gameOver();
					}
					if (creature.getCurrentHealth() <= 0) {
						System.out.println("You won!!!");
						this.leaveBattle();
					}
				}
			}
		}
	}
	
	@Override
	public void onEnter() {
		Handler.getAudioManager().playTrack(Tracks.Battle);
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
		//System.out.println(Arrays.toString(turnResults));
		return turnResults;
	}

	public String getRandomAction() {
		Integer randNumber = Utilities.getRandomNumber(1, actions.length);
		System.out.println(randNumber - 1);
		System.out.println(actions[randNumber - 1]);
		return actions[randNumber - 1];
	}

	public void leaveBattle() {
		Utilities.Debug("Leaving battle!");
		this.stateManager.leaveState();
		Handler.getAudioManager().stopCurrentTrack();
		Handler.getAudioManager().playTrack(Tracks.Overworld);
		Handler.getWorld().getEntityManager().getPlayer().setGoToBattle(false);

		Handler.getPlayer().setCurrentExperience(Handler.getPlayer().getCurrentExperience() + creature.getExpToGive());// DBC
		Handler.getPlayer().getStats();// DBC
		creature.setCurrentHealth(10); // DBC
	}

	public String Attack(Creature attacker, Creature defender) {
		String attackMessage = null;
		Integer defenderDefense = defender.getDefense();
		if (defender.isDefending()) {
			defenderDefense = defenderDefense * 2;
			Utilities.Debug("Defending! Doubling defense to " + defenderDefense);
			defender.setDefending(false);
		}
		Integer enemyAttack = Utilities.getRandomNumber(0, attacker.getAttack()) - defenderDefense;
		if (enemyAttack > 0) {
			attackMessage = defender.getEntityName() + " takes:" + enemyAttack + " damage!";
			Utilities.Debug(attackMessage);
			defender.setCurrentHealth(defender.getCurrentHealth() - enemyAttack);
		} else {
			attackMessage = attacker.getEntityName() + " missed!";
			Utilities.Debug(attacker.getEntityName() + " missed!");
		}
		System.out.println(attackMessage);
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

		// System.out.println("I was called!");

		g.clearRect(0, 0, Handler.getWidth(), Handler.getHeight());
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
				(int) (((double) Handler.getPlayer().getCurrentHealth() / (double) Handler.getPlayer().getMaxHealth())
						* healthBarWidth),
				healthBarHeight);
		g.fillRect(Handler.getWidth() / 2, Handler.getHeight() / 3 + 150,
				(int) (((double) creature.getCurrentHealth() / (double) creature.getMaxHealth()) * healthBarWidth),
				healthBarHeight);

		g.setColor(java.awt.Color.BLACK);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(attackButton);
		g2d.draw(defendButton);
		g.drawString("Attack", attackButton.x, attackButton.y + (int) (attackButton.getWidth() / 2));
		g.drawString("Defend", defendButton.x, defendButton.y + (int) (defendButton.getWidth() / 2));

		if (turnResults != null) {
			g.setFont(f);
			for (int i = 0; i < turnResults.length; i++) {
				if (turnResults[i] != null) {
					g.drawString(turnResults[i], 300, 500 + ((i - 1) * 50));
				}
			}
		}
	}
}
