package nick.dev.base.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.tiles.Tile;

/**
 * This abstract class is the basis for all non-static entities. Extends the Entity
 * class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public abstract class Creature extends Entity {
	protected Animation animDown, animUp, animLeft, animRight;

	public static final int DEFAULT_HEALTH = 10;
	public static final int DEFAULT_ATTACK = 1;
	public static final int DEFAULT_DEFENSE = 0;
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_ANIM_SPEED = 1000;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

	protected int currentHealth;
	protected int maxHealth;
	protected int attack;
	protected int defense;
	protected float speed;
	protected int level;
	protected int expToGive;
	protected float xMove, yMove;
	protected boolean isDefending = false;

	public int getExpToGive() {
		return expToGive;
	}

	public void setExpToGive(int expToGive) {
		this.expToGive = expToGive;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Creature(float x, float y, int width, int height) {
		super(x, y, width, height);
		currentHealth = DEFAULT_HEALTH;
		maxHealth = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		attack = DEFAULT_ATTACK;
		defense = DEFAULT_DEFENSE;
		animDown = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_DOWN);
		animUp = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_UP);
		animLeft = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_LEFT);
		animRight = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_RIGHT);
		xMove = 0;
		yMove = 0;
	}

	void randomMove(int randX, int randY) {
	}

	@Override
	public void update() {
		animDown.update();
		animUp.update();
		animLeft.update();
		animRight.update();
		this.move();
	}

	public boolean isDefending() {
		return isDefending;
	}

	public void setDefending(boolean isDefending) {
		this.isDefending = isDefending;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void move() {
		if (!checkEntityCollisions(xMove, 0f)) {
			moveX();
		}
		if (!checkEntityCollisions(0f, yMove)) {
			moveY();
		}
	}

	public void moveX() {
		if (xMove > 0) {// Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}

		} else if (xMove < 0) {// Moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}

		}
	}

	public void moveY() {
		if (yMove < 0) {// Up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}

		} else if (yMove > 0) {// Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}

		}
	}

	protected boolean collisionWithTile(int x, int y) {
		return Handler.getWorld().getTile(x, y).isSolid();
	}

	public BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0) {
			return animLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return animRight.getCurrentFrame();
		} else if (yMove < 0) {
			return animUp.getCurrentFrame();
		} else {
			return animDown.getCurrentFrame();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - Handler.getGameCamera().getxOffset()),
				(int) (y - Handler.getGameCamera().getyOffset()), width, height, null);
		// g.setColor(Color.red);
		// g.fillRect((int) (x + bounds.x -
		// handler.getGameCamera().getxOffset()),
		// (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
		// bounds.width, bounds.height);

	}

	public Animation getAnimDown() {
		return animDown;
	}

	public Animation getAnimUp() {
		return animUp;
	}

	public Animation getAnimLeft() {
		return animLeft;
	}

	public Animation getAnimRight() {
		return animRight;
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void warp(int warpX, int warpY) {
		this.setX(warpX);
		this.setY(warpY);
		System.out.println("You got warped, son!");

	}

}
