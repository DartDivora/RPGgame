package nick.dev.items;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.combat.Spell;
import nick.dev.utilities.Utilities;

public class Item {

	private String itemID;
	private String itemName;

	private static HashMap<String, Item> itemMap = new HashMap<String, Item>();
	private static HashMap<String, Method> itemAction;

	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("itemJSON"));
		itemMap = gson.fromJson(JSONString, new TypeToken<HashMap<String, Item>>() {}.getType());
		itemAction = new HashMap<String, Method>();
		for (Entry<String, Item> entry : itemMap.entrySet()) {
			/*
			 * try { Class[] params = new Class[2]; params[0] = Stats.class;
			 * params[1] = Stats.class;
			 * itemAction.put(entry.getValue().getItemName(),
			 * Spell.class.getMethod(entry.getValue().getItemName(), params)); }
			 * catch (NoSuchMethodException | SecurityException e) {
			 * System.out.println("No method exists for: " +
			 * entry.getValue().getItemName()); e.printStackTrace(); }
			 */
			entry.getValue().initialize();
			System.out.println(entry.getValue().getItemName());
		}

		System.out.println(itemMap.entrySet());
	}

	public static HashMap<String, Item> getItemMap() {
		return itemMap;
	}

	public void initialize() {
	}

	public String getItemID() {
		return itemID;
	}

	public String getItemName() {
		return itemName;
	}

}
