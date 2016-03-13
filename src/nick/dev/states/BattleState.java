package nick.dev.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;

import nick.dev.base.Handler;
import nick.dev.base.entities.Creature;
import nick.dev.base.entities.Gnoll;
import nick.dev.utilities.Utilities;

public class BattleState extends State {
	private Gnoll creature;
	private String[] actions;
	public int mainButtonWidth, mainButtonHeight, attackButtonX, attackButtonY, defendButtonX, defendButtonY;
	public Rectangle attackButton, defendButton;
	int healthBarWidth, healthBarHeight, creatureDisplayWidth, creatureDisplayHeight;
	String playerAction = null;
	String[] turnResults = null;

	public BattleState(Handler handler) {
		super(handler);

		actions = new String[2];
		actions[0] = "Attack";
		actions[1] = "Defend";

		f = new Font("arial", Font.BOLD, 25);
		healthBarWidth = Integer.parseInt(Utilities.getPropValue("healthBarWidth", Utilities.getPropFile()));
		healthBarHeight = Integer.parseInt(Utilities.getPropValue("healthBarHeight", Utilities.getPropFile()));
		creatureDisplayWidth = Integer
				.parseInt(Utilities.getPropValue("creatureDisplayWidth", Utilities.getPropFile()));
		creatureDisplayHeight = Integer
				.parseInt(Utilities.getPropValue("createDisplayHeight", Utilities.getPropFile()));

		mainButtonWidth = handler.getWidth() / 4;
		mainButtonHeight = handler.getHeight() / 5;
		attackButtonX = 0;
		attackButtonY = handler.getHeight() - (handler.getHeight() / 4);
		defendButtonX = mainButtonWidth;
		defendButtonY = attackButtonY;

		attackButton = new Rectangle(attackButtonX, attackButtonY, mainButtonWidth, mainButtonHeight);
		defendButton = new Rectangle(defendButtonX, defendButtonY, mainButtonWidth, mainButtonHeight);

		creature = new Gnoll(handler, 0, 0, 0, 0);
	}

	@Override
	public void update() {
		handler.getWorld().getEntityManager().getPlayer().update();
		creature.update();
		playerAction = null;
		if (handler.getMouseManager().getX() != null || handler.getMouseManager().getY() != null) {
			if (this.inMouseBounds(attackButton, handler.getMouseManager().getX(), handler.getMouseManager().getY())) {
				playerAction = "Attack";
			} else if (this.inMouseBounds(defendButton, handler.getMouseManager().getX(),
					handler.getMouseManager().getY())) {
				playerAction = "Defend";
			}

			if (playerAction != null) {
				if (handler.getPlayer().getCurrentHealth() > 0 && creature.getCurrentHealth() > 0) {
					this.doAction(playerAction, this.getRandomAction());

					if (handler.getPlayer().getCurrentHealth() <= 0) {
						System.out.println("Game over, man!");
						this.gameOver();
					}
					if (creature.getCurrentHealth() <= 0) {
						System.out.println("You won!!!");
						this.leaveBattle();
					}
				}
			}
			handler.getMouseManager().resetXY();
		}
	}

	public String[] doAction(String playerAction, String enemyAction) {
		turnResults = new String[2];
		if (playerAction.equals("Defend")) {
			turnResults[0] = Defend(handler.getPlayer());
		}
		if (enemyAction.equals("Defend")) {
			turnResults[1] = Defend(creature);
		}
		if (playerAction.equals("Attack")) {
			turnResults[0] = Attack(handler.getPlayer(), creature);
		}
		if (enemyAction.equals("Attack")) {
			turnResults[1] = Attack(creature, handler.getPlayer());
		}
		System.out.println(Arrays.toString(turnResults));
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
		handler.getWorld().getEntityManager().getPlayer().setGoToBattle(false);
		State.setState(getReturnState());
		handler.getAudioManager().stop();
		handler.getAudioManager().Track(Utilities.getPropValue("musicOverworld", Utilities.getPropFile()));
		handler.getAudioManager().play();
		handler.getGame().setInBattle(false);
		handler.getWorld().getEntityManager().getPlayer().setGoToBattle(false);

		handler.getPlayer().setCurrentExperience(handler.getPlayer().getCurrentExperience() + creature.getExpToGive());// DBC
		handler.getPlayer().getStats();// DBC
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
		handler.getGame().stop();
	}

	@Override
	public void render(Graphics g) {
		g.setFont(f);

		// System.out.println("I was called!");

		g.clearRect(0, 0, handler.getWidth(), handler.getHeight());
		g.drawImage(handler.getWorld().getEntityManager().getPlayer().getAnimDown().getCurrentFrame(),
				handler.getWidth() / 6, handler.getHeight() / 3, creatureDisplayWidth, creatureDisplayHeight, null);
		g.drawImage(creature.getAnimDown().getCurrentFrame(), handler.getWidth() / 2, handler.getHeight() / 3,
				creatureDisplayWidth, creatureDisplayHeight, null);
		g.drawString("This is a battle?", handler.getWidth() / 3, (handler.getHeight() / 6));
		g.setColor(java.awt.Color.red);
		g.fillRect(handler.getWidth() / 6, handler.getHeight() / 3 + 150, healthBarWidth, healthBarHeight);
		g.fillRect(handler.getWidth() / 2, handler.getHeight() / 3 + 150, healthBarWidth, healthBarHeight);
		g.setColor(java.awt.Color.green);
		g.fillRect(handler.getWidth() / 6, handler.getHeight() / 3 + 150,
				(int) (((double) handler.getPlayer().getCurrentHealth() / (double) handler.getPlayer().getMaxHealth())
						* healthBarWidth),
				healthBarHeight);
		g.fillRect(handler.getWidth() / 2, handler.getHeight() / 3 + 150,
				(int) (((double) creature.getCurrentHealth() / (double) creature.getMaxHealth()) * healthBarWidth),
				healthBarHeight);

		g.setColor(java.awt.Color.BLACK);
		g2d = (Graphics2D) g;
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
