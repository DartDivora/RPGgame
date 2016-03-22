package nick.dev.entities;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.combat.Stats;
import nick.dev.utilities.Utilities;

/**************************************************************
 * Information about a monster. Animation (or just sprite), 
 * stats and name right now.
 * 
 * @author nsanft,acharles
 * @version 1.1
 **************************************************************/
public class Monster {
	
	/**************************************************************
	 * Stores the data for the monsters in the game. Reads from
	 * a JSON file and loads the data. When creating a new monster
	 * in the game, it will use this collection as the template.
	 **************************************************************/
	private static HashMap<String, Monster> monsterData;
	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("monsterJSON"));
		
		monsterData = gson.fromJson(JSONString, new TypeToken<HashMap<String, Monster>>(){}.getType());
		
		for (Entry<String, Monster> entry : monsterData.entrySet()) {
			entry.getValue().printStats();
		}
	}
	/*************************************************************/
	
	private String name;
	private Stats stats;

	// May not need this.
	public Monster() {
	}
	
	public void printStats() {
		this.stats.printStats();
	}
	
	public Stats getStats() {
		return this.stats;
	}
	
}
