package nick.dev.states;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Stack;

import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;

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
		states.put(State.Types.Title, new TitleState(this));
		states.put(State.Types.Overworld, new OverworldState(this));
		states.put(State.Types.Battle, new BattleState(this));
		states.put(State.Types.Dialog, new DialogState(this));
		states.put(State.Types.Menu, new MenuState(this));
		states.put(State.Types.GameOver, new GameOverState(this));

		this.changeState(State.Types.Title);
	}

	public void update() {
		currentStateStack.peek().update();
		if (Handler.getKeyManager().keyIsPressed(Keys.Quit)) {
			System.exit(0);
		}
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

	// Overload in case you want to send arguments.
	// Probably need to make this a little better so it's not repeating code.
	public void changeState(State.Types newStateType, StateArgument args) {
		if (!currentStateStack.isEmpty()) {
			currentStateStack.peek().onExit();
		}

		this.currentStateStack.push(this.states.get(newStateType));
		this.currentStateStack.peek().onEnter(args);
	}

	public boolean leaveState() {
		if (!currentStateStack.isEmpty()) {
			State state = currentStateStack.pop();
			state.onExit();
			
			this.currentStateStack.peek().onEnter();
			return true;
		} else {
			return false;
		}
	}
}
