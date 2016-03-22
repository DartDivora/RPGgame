package nick.dev.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import nick.dev.base.Handler;

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
//		JSONObject jo = new JSONObject();
//		try {
//			jo.put(saveFileKeys[0], Handler.getPlayer().getEntityName());
//			jo.put(saveFileKeys[1], Handler.getPlayer().getStrength());
//			jo.put(saveFileKeys[2], Handler.getPlayer().getVitality());
//			jo.put(saveFileKeys[3], Handler.getPlayer().getIntelligence());
//			jo.put(saveFileKeys[4], Handler.getPlayer().getWisdom());
//			jo.put(saveFileKeys[5], Handler.getPlayer().getDexterity());
//			jo.put(saveFileKeys[6], Handler.getPlayer().getLuck());
//			jo.put(saveFileKeys[7], Handler.getPlayer().getDefense());
//			jo.put(saveFileKeys[8], Handler.getPlayer().getCurrentHP());
//			jo.put(saveFileKeys[9], Handler.getPlayer().getMaxHP());
//			jo.put(saveFileKeys[10], Handler.getPlayer().getCurrentMP());
//			jo.put(saveFileKeys[11], Handler.getPlayer().getMaxMP());
//			jo.put(saveFileKeys[12], Handler.getPlayer().getSpeed());
//			jo.put(saveFileKeys[13], Handler.getPlayer().getCurrentExperience());
//		} catch (JSONException e) {
//			System.out.println("Failed to save file: " + saveFile);
//		}
//		System.out.println(jo.toString());
//		try {
//			Path p = Paths.get(saveFile);
//			Files.deleteIfExists(p);
//			PrintWriter out = new PrintWriter(saveFile);
//			out.write(jo.toString());
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return true;
	}

	public void loadGame(String saveFile) {
//		String[] loadResults = Utilities.getFromJSONObject(saveFile, saveFileKeys);
//		Handler.getPlayer().setEntityName(loadResults[0]);
//		Handler.getPlayer().setStrength(Integer.parseInt(loadResults[1]));
//		Handler.getPlayer().setVitality(Integer.parseInt(loadResults[2]));
//		Handler.getPlayer().setIntelligence(Integer.parseInt(loadResults[3]));
//		Handler.getPlayer().setWisdom(Integer.parseInt(loadResults[4]));
//		Handler.getPlayer().setDexterity(Integer.parseInt(loadResults[5]));
//		Handler.getPlayer().setLuck(Integer.parseInt(loadResults[6]));
//		Handler.getPlayer().setDefense(Integer.parseInt(loadResults[7]));
//		Handler.getPlayer().setCurrentHP(Integer.parseInt(loadResults[8]));
//		Handler.getPlayer().setMaxHP(Integer.parseInt(loadResults[9]));
//		Handler.getPlayer().setCurrentMP(Integer.parseInt(loadResults[10]));
//		Handler.getPlayer().setMaxMP(Integer.parseInt(loadResults[11]));
//		Handler.getPlayer().setSpeed(Integer.parseInt(loadResults[12]));
//		Handler.getPlayer().setCurrentExperience(Integer.parseInt(loadResults[13]));
	}
}
