package nick.dev.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Utilities extends IOUtils {

	private static boolean Debug = false;
	private static String propFile = "res/properties/rpg.properties";

	public static void Debug(String message) {
		if (Debug) {
			System.out.println(message);
		}
	}

	public static void Debug(Integer message) {
		if (Debug) {
			System.out.println(message);
		}
	}

	public static void Debug(boolean message) {
		if (Debug) {
			System.out.println(message);
		}
	}

	public boolean isDebug() {
		return Debug;
	}

	public void setDebug(boolean debug) {
		Debug = debug;
	}

	public static Integer getRandomNumber(Integer min, Integer max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	public static String[] getFromJSONObject(String path, String[] keys) {
		File f = new File(path);
		String jsonTxt = null;
		InputStream is = null;
		String[] result = new String[keys.length];
		JSONObject jo = null;
		if (f.exists()) {
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				System.out.println("Could not find the file: " + f);
				e.printStackTrace();
			}
			try {
				jsonTxt = IOUtils.toString(is, "UTF-8");
			} catch (IOException e) {
				System.out.println("Could not convert the file: " + is + " to a String!!!");
				e.printStackTrace();
			}
			Debug(jsonTxt);
			try {
				jo = new JSONObject(jsonTxt);
			} catch (JSONException e) {
				System.out.println("An error occurred parsing the .json file to a JSON object!!!");
				e.printStackTrace();
			}
			// Debug(jo.toString());
		} else {
			Debug("File: " + path + " did not exist!!!");
			return null;
		}
		for (int i = 0; i < keys.length; i++) {
			try {
				// Debug(keys[i]);
				// Debug(jo.get(keys[i]).toString());
				result[i] = jo.get(keys[i]).toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Debug(Arrays.toString(result));
		return result;
	}

	public static String getPropValue(String PropertyName, String propFileName) {
		String result = "";
		Properties prop = new Properties();
		// String propFileName = "config.properties";

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(propFileName);

			prop.load(inputStream);
		} catch (IOException e) {
			Debug("An error occurred when reading the file: " + propFileName);
			e.printStackTrace();
		}
		// this sets the property value from the file.
		result = prop.getProperty(PropertyName);
		// System.out.println(PropertyName + "-" + result);

		if (result == null) {
			System.out.println("Property value " + PropertyName + " not found!");
			// throw new RuntimeException();

		}
		return result;

	}

	public static String getPropFile() {
		return propFile;
	}

	public static void setPropFile(String propFile) {
		Utilities.propFile = propFile;
	}

	public static boolean battleChance(Integer chanceOfBattle) {
		Integer randomBattleNum = getRandomNumber(1, 100);
		if (randomBattleNum <= chanceOfBattle) {
			Debug("Battle time! " + randomBattleNum + " was less than or equal to " + chanceOfBattle);
			return true;
		} else {
			Debug("No Battle.");
			return false;
		}
	}

	public static void wait(Integer numberOfUnits, String unitOfTime) {
		switch (unitOfTime) {
		case "Seconds":
			try {
				TimeUnit.SECONDS.sleep(numberOfUnits);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		case "Minutes":
			try {
				TimeUnit.MINUTES.sleep(numberOfUnits);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
