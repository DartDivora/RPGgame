package nick.dev.states;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Stack;

/**
 * This class manages states. Enough said.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class StateManager {

	private HashMap<State.Types, State> states = new HashMap<State.Types, State>();
	private Stack<State> currentStateStack = new Stack<State>();

	public StateManager() {
		states.put(State.Types.Menu, new MenuState(this));
		states.put(State.Types.Overworld, new GameState(this));
		states.put(State.Types.Battle, new BattleState(this));
		states.put(State.Types.Dialog, new DialogState(this));

		this.changeState(State.Types.Menu);
	}

	public void update() {
		currentStateStack.peek().update();
	}

	public void render(Graphics graphics) {
		currentStateStack.peek().render(graphics);
	}

	public void changeState(State.Types newStateType) {
		if (!currentStateStack.isEmpty()) {
			currentStateStack.peek().onExit();
		}

		this.currentStateStack.push(this.states.get(newStateType));
		this.currentStateStack.peek().onEnter();
	}

	public boolean leaveState() {
		if (!currentStateStack.isEmpty()) {
			State state = currentStateStack.pop();
			state.onExit();
			return true;
		} else {
			return false;
		}
	}
}
