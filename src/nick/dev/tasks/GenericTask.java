package nick.dev.tasks;

public class GenericTask extends Task {
	
	private Runnable action;
	
	public GenericTask(Runnable action) {
		this.action = action;
	}
	
	public void update() {
		this.action.run();
		this.finished = true;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
}
