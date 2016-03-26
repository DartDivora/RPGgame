package nick.dev.tasks;

import java.awt.Graphics;

public abstract class Task {
	
	protected boolean finished = false;
	
	public Task() {
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	public boolean isFinished() {
		return this.finished;
	}
}
