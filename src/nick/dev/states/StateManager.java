package nick.dev.states;

import java.util.HashMap;
import java.util.Stack;

public class StateManager {
	
	private HashMap<State.Types, State> states = new HashMap<State.Types, State>();
	private Stack<State> currentStateStack = new Stack<State>();
	
	public StateManager() {
		
	}
	
	public void update() {
		currentStateStack.peek().update();
	}
	
	public void render() {
		
	}
	
	public void changeState(State.Types newStateType) {
		if (!currentStateStack.isEmpty()) {
			// currentStateStack.Peek().onExit();
		}
		
		this.currentStateStack.push(this.states.get(newStateType));
		// this.currentStateStack.peek().onEnter();
	}
	
	public boolean leaveState() {
		if (!currentStateStack.isEmpty()) {
			State state = currentStateStack.pop();
			// state.onExit();
			return true;
		} else {
			return false;
		}
	}
}
