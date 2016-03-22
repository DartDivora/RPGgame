package nick.dev.dialog;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.utilities.Utilities;

/***************************************************************
 * This class manages all dialog output in the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 **************************************************************/
public class DialogManager {
	
	/**************************************************************
	 * Stores the data for the dialogue in the game.
	 **************************************************************/
	private static HashMap<String, String> dialogueData;
	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("dialogueJSON"));
		
		dialogueData = gson.fromJson(JSONString, new TypeToken<HashMap<String, String>>(){}.getType());
	}

	/**************************************************************
	 * Constructor
	 **************************************************************/
	public DialogManager() {
		// Not a thang
	}
	
	/**************************************************************
	 * Gets the line of dialogue associated with the given ID.
	 **************************************************************/
	public String getLine(Integer id) {
		String index = id.toString();
		return DialogManager.dialogueData.get(index);
	}
}
