package nick.dev.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import nick.dev.base.Handler;

public class SaveManager {

	public static String[] saveFileKeys;

	public SaveManager() {
		saveFileKeys = new String[7];
		saveFileKeys[0] = "CharacterName";
		saveFileKeys[1] = "Attack";
		saveFileKeys[2] = "Defense";
		saveFileKeys[3] = "CurrentHealth";
		saveFileKeys[4] = "MaxHealth";
		saveFileKeys[5] = "Speed";
		saveFileKeys[6] = "CurrentExperience";
	}

	public boolean saveGame(Handler handler, String saveFile) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(saveFileKeys[0], handler.getPlayer().getEntityName());
			jo.put(saveFileKeys[1], handler.getPlayer().getAttack());
			jo.put(saveFileKeys[2], handler.getPlayer().getDefense());
			jo.put(saveFileKeys[3], handler.getPlayer().getCurrentHealth());
			jo.put(saveFileKeys[4], handler.getPlayer().getMaxHealth());
			jo.put(saveFileKeys[5], handler.getPlayer().getSpeed());
			jo.put(saveFileKeys[6], handler.getPlayer().getCurrentExperience());
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

	public Handler loadGame(Handler handler, String saveFile) {
		String[] loadResults = Utilities.getFromJSONObject(saveFile, saveFileKeys);
		handler.getPlayer().setEntityName(loadResults[0]);
		handler.getPlayer().setAttack(Integer.parseInt(loadResults[1]));
		handler.getPlayer().setDefense(Integer.parseInt(loadResults[2]));
		handler.getPlayer().setCurrentHealth(Integer.parseInt(loadResults[3]));
		handler.getPlayer().setMaxHealth(Integer.parseInt(loadResults[4]));
		handler.getPlayer().setSpeed(Float.parseFloat(loadResults[5]));
		handler.getPlayer().setCurrentExperience(Integer.parseInt(loadResults[6]));
		return handler;
	}
}
