package nick.dev.dialog;

import java.util.HashMap;

import nick.dev.base.Handler;

/**
 * This class manages all dialog output in the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class DialogManager {
	@SuppressWarnings("unused")
	private Handler handler;
	private HashMap<Integer, String> dialogMap;

	public DialogManager(Handler handler) {
		this.handler = handler;
		this.dialogMap = new HashMap<Integer, String>();

		dialogMap.put(0, "Hello, good friend.");
		dialogMap.put(1, "Goodbye, good friend.");

		this.getDialog(0);
	}

	public void printDialogMap() {
		System.out.println(dialogMap.entrySet());
	}

	public void getDialog(Integer dialogID) {
		dialogMap.get(dialogID);
	}

}
