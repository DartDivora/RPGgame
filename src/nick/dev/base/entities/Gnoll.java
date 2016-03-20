package nick.dev.base.entities;

import nick.dev.utilities.Utilities;

/**
 * This is a Gnoll, just a regular old placeholder enemy.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Gnoll extends Creature {

	public Gnoll(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setDefense(0);
		this.setStrength(3);
		this.setCurrentHP(10);
		this.setExpToGive(100);
	}

	@Override
	void randomMove(int randX, int randY) {
		this.xMove = randX;
		this.yMove = randY;
	}

	@Override
	public void update() {
		animDown.update();
		animUp.update();
		animLeft.update();
		animRight.update();
		this.randomMove(Utilities.getRandomNumber(-1, 1), Utilities.getRandomNumber(-1, 1));
		this.move();
	}

}
