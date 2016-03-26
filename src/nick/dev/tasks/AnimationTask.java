package nick.dev.tasks;

import java.awt.Graphics;

import nick.dev.gfx.Animation;

public class AnimationTask extends Task {
	
	private Animation animation;
	private Integer x;
	private Integer y;

	public AnimationTask(Animation anim, Integer x, Integer y) {
		this.animation = anim;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		this.animation.update();
		if (this.animation.isFinished()) {
			this.finished = true;
		}
	}
	
	public void render(Graphics g) {
		this.animation.render(g, this.x, this.y);
	}
}
