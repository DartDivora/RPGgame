package nick.dev.base.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nick.dev.base.Handler;
import nick.dev.gfx.Animation;
import nick.dev.gfx.Assets;
import nick.dev.maps.Map;

/**
 * This abstract class is the basis for all non-static entities. Extends the
 * Entity class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public abstract class Creature extends Entity {
	protected Animation animDown, animUp, animLeft, animRight;

	public static final int DEFAULT_HP = 10;
	public static final int DEFAULT_MP = 0;
	public static final int DEFAULT_STRENGTH = 1;
	public static final int DEFAULT_INTELLIGENCE = 1;
	public static final int DEFAULT_DEXTERITY = 1;
	public static final int DEFAULT_LUCK = 1;
	public static final int DEFAULT_DEFENSE = 0;
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_ANIM_SPEED = 1000;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

	protected int currentHP, maxHP;
	protected int currentMP, maxMP;
	protected int defense;
	protected float speed;
	protected int level;
	protected int expToGive;
	protected float xMove, yMove;
	protected boolean isDefending = false;
	public int strength, vitality, intelligence, wisdom, dexterity, luck;

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}

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
		currentHP = DEFAULT_HP;
		maxHP = DEFAULT_HP;
		currentMP = DEFAULT_MP;
		maxMP = DEFAULT_MP;
		speed = DEFAULT_SPEED;
		strength = DEFAULT_STRENGTH;
		intelligence = DEFAULT_INTELLIGENCE;
		defense = DEFAULT_DEFENSE;
		animDown = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_DOWN);
		animUp = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_UP);
		animLeft = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_LEFT);
		animRight = new Animation(DEFAULT_ANIM_SPEED, Assets.DEFAULT_ANIM_RIGHT);
		xMove = 0;
		yMove = 0;
		this.setMaxHP();
		this.setMaxMP();
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
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Map.TileWidth;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Map.TileHeight)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Map.TileHeight)) {
				x += xMove;
			} else {
				x = tx * Map.TileWidth - bounds.x - bounds.width - 1;
			}

		} else if (xMove < 0) {// Moving left
			int tx = (int) (x + xMove + bounds.x) / Map.TileWidth;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Map.TileHeight)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Map.TileHeight)) {
				x += xMove;
			} else {
				x = tx * Map.TileWidth + Map.TileWidth - bounds.x;
			}

		}
	}

	public void moveY() {
		if (yMove < 0) {// Up
			int ty = (int) (y + yMove + bounds.y) / Map.TileHeight;

			if (!collisionWithTile((int) (x + bounds.x) / Map.TileWidth, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Map.TileWidth, ty)) {
				y += yMove;
			} else {
				y = ty * Map.TileHeight + Map.TileHeight - bounds.y;
			}

		} else if (yMove > 0) {// Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Map.TileHeight;

			if (!collisionWithTile((int) (x + bounds.x) / Map.TileWidth, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Map.TileWidth, ty)) {
				y += yMove;
			} else {
				y = ty * Map.TileHeight - bounds.y - bounds.height - 1;
			}

		}
	}

	protected boolean collisionWithTile(int x, int y) {
		return Handler.getWorld().tileIsSolid(x, y);
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

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP() {
		this.maxHP = 10 + (this.getVitality() * 4);
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getCurrentMP() {
		return currentMP;
	}

	public void setCurrentMP(int currentMP) {
		this.currentMP = currentMP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public void setMaxMP() {
		this.maxMP = (wisdom * 5);
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
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
