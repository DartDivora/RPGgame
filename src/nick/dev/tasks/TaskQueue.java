package nick.dev.tasks;

import java.awt.Graphics;
import java.util.LinkedList;

public class TaskQueue {

	private Task currentTask;
	private LinkedList<Task> waitingTasks = new LinkedList<Task>();
	
	public TaskQueue() {
		
	}
	
	public void update() {
		if (currentTask != null) {
			currentTask.update();
			
			if (currentTask.isFinished()) {
				currentTask = waitingTasks.pop();
			}
		}
	}
	
	public void render(Graphics g) {
		if (currentTask != null) {
			currentTask.render(g);
		}
	}
	
	public void addTask(Task t) {
		waitingTasks.add(t);
	}
	
}
