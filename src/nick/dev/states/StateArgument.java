package nick.dev.states;

/**
 * This class allows arguments to be passed between states.
 * 
 * @author acharles,nsanft
 *
 */
public class StateArgument {

	private Integer dialogLineID = 0;

	public Integer getDialogLine() {
		return dialogLineID;
	}

	public void setDialogLine(Integer lineID) {
		this.dialogLineID = lineID;
	}

}
