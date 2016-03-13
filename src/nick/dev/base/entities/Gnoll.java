package nick.dev.base.entities;

import nick.dev.base.Handler;
import nick.dev.utilities.Utilities;

public class Gnoll extends Creature {

	public Gnoll(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.setDefense(0);
		this.setAttack(3);
		this.setCurrentHealth(10);
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
