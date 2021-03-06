package nick.dev.utilities;

import java.awt.Rectangle;
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

import nick.dev.maps.Map;

/**
 * This class contains all methods that do not otherwise fit into a specific
 * class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Utilities extends IOUtils {

	private static boolean Debug = true;
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

	public static String getStringFromFile(String path) {
		File f = new File(path);
		InputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file: " + f);
			e.printStackTrace();
		}
		try {
			return IOUtils.toString(is, "UTF-8");
		} catch (IOException e) {
			System.out.println("Could not load the JSONObject from the path: " + path);
			e.printStackTrace();
			return null;
		}
	}

	// This kind of works - it returns the longest number of characters, 
	// but what the highlight does is use the one with the longest pixel
	// length in the current font. So in the battle menu, with Attack
	// and Defend, it doesn't work since they're the same length but 
	// "Defend" is more pixels wide than "Attack".
	public static String getLongestString(String[] sa) {
		Integer maxLength = 0;
		String longest = null;
		for (String s : sa) {
			if (s.length() > maxLength) {
				maxLength = s.length();
				longest = s;
			}
		}
		return longest;
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
		} else {
			Debug("File: " + path + " did not exist!!!");
			return null;
		}
		for (int i = 0; i < keys.length; i++) {
			try {
				result[i] = jo.get(keys[i]).toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Debug(Arrays.toString(result));
		return result;
	}

	public static String getPropValue(String PropertyName) {
		String result = "";
		Properties prop = new Properties();
		// String propFileName = "config.properties";

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(propFile);

			prop.load(inputStream);
		} catch (IOException e) {
			Debug("An error occurred when reading the file: " + propFile);
			e.printStackTrace();
		}
		// this sets the property value from the file.
		result = prop.getProperty(PropertyName);

		if (result == null) {
			System.out.println("Property value " + PropertyName + " not found!");
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
	
	public static Rectangle tileToBoundingBox(int x, int y) {
		int rectX = x * Map.TileWidth;
		int rectY = y * Map.TileHeight;
		
		return new Rectangle(rectX, rectY, Map.TileWidth, Map.TileHeight);
	}

}
