package nick.dev.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import nick.dev.base.Handler;
import nick.dev.entities.Player;

/**
 * This class manages the saving and loading of the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class SaveManager {

	public static String[] saveFileKeys;

	public SaveManager() {
		saveFileKeys = new String[14];
		saveFileKeys[0] = "CharacterName";
		saveFileKeys[1] = "Strength";
		saveFileKeys[2] = "Vitality";
		saveFileKeys[3] = "Intelligence";
		saveFileKeys[4] = "Wisdom";
		saveFileKeys[5] = "Dexterity";
		saveFileKeys[6] = "Luck";
		saveFileKeys[7] = "Defense";
		saveFileKeys[8] = "CurrentHP";
		saveFileKeys[9] = "MaxHP";
		saveFileKeys[10] = "CurrentMP";
		saveFileKeys[11] = "MaxMP";
		saveFileKeys[12] = "Speed";
		saveFileKeys[13] = "CurrentExperience";
	}

	public boolean saveGame(String saveFile) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(saveFileKeys[0], Handler.getWorld().getEntityManager().getPlayer().getName());
			jo.put(saveFileKeys[1], Player.getStats().getStrength());
			jo.put(saveFileKeys[2], Player.getStats().getVitality());
			jo.put(saveFileKeys[3], Player.getStats().getIntelligence());
			jo.put(saveFileKeys[4], Player.getStats().getWisdom());
			jo.put(saveFileKeys[5], Player.getStats().getDexterity());
			jo.put(saveFileKeys[6], Player.getStats().getLuck());
			jo.put(saveFileKeys[7], Player.getStats().getDefense());
			jo.put(saveFileKeys[8], Player.getStats().getCurrentHP());
			jo.put(saveFileKeys[9], Player.getStats().getMaxHP());
			jo.put(saveFileKeys[10], Player.getStats().getCurrentMP());
			jo.put(saveFileKeys[11], Player.getStats().getMaxMP());
			jo.put(saveFileKeys[12], Player.getStats().getSpeed());
			jo.put(saveFileKeys[13], Player.getStats().getCurrentExp());
		} catch (JSONException e) {
			System.out.println("Failed to save file: " + saveFile);
		}
		System.out.println(jo.toString());
		try {
			Path p = Paths.get(saveFile);
			Files.deleteIfExists(p);
			PrintWriter out = new PrintWriter(saveFile);
			out.write(jo.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void loadGame(String saveFile) {
		String[] loadResults = Utilities.getFromJSONObject(saveFile, saveFileKeys);
		Handler.getWorld().getEntityManager().getPlayer().setName(loadResults[0]);
		Player.getStats().setStrength(Integer.parseInt(loadResults[1]));
		Player.getStats().setVitality(Integer.parseInt(loadResults[2]));
		Player.getStats().setIntelligence(Integer.parseInt(loadResults[3]));
		Player.getStats().setWisdom(Integer.parseInt(loadResults[4]));
		Player.getStats().setDexterity(Integer.parseInt(loadResults[5]));
		Player.getStats().setLuck(Integer.parseInt(loadResults[6]));
		Player.getStats().setDefense(Integer.parseInt(loadResults[7]));
		Player.getStats().setCurrentHP(Integer.parseInt(loadResults[8]));
		Player.getStats().setMaxHP(Integer.parseInt(loadResults[9]));
		Player.getStats().setCurrentMP(Integer.parseInt(loadResults[10]));
		Player.getStats().setMaxMP(Integer.parseInt(loadResults[11]));
		Player.getStats().setSpeed(Integer.parseInt(loadResults[12]));
		Player.getStats().setCurrentExp(Integer.parseInt(loadResults[13]));
	}
}
