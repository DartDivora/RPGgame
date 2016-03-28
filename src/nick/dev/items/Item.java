package nick.dev.items;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.utilities.Utilities;

/**
 * This class represents a single item in the RPG.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Item {

	private String itemID;
	private String itemName;
	private String itemType;
	private String itemDescription;
	private int healing;
	private String slotType;

	private static HashMap<String, Item> itemMap = new HashMap<String, Item>();

	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("itemJSON"));
		itemMap = gson.fromJson(JSONString, new TypeToken<HashMap<String, Item>>() {
		}.getType());
		for (Entry<String, Item> entry : itemMap.entrySet()) {
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

	public String getItemType() {
		return itemType;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public int getHealing() {
		return healing;
	}

	public String getSlotType() {
		return slotType;
	}

	public void printItem() {
		System.out.println("Item ID: " + this.getItemID());
		System.out.println("Item Name: " + this.getItemName());
		System.out.println("Item Description: " + this.getItemDescription());
		System.out.println("Item Healing: " + this.getHealing());
		System.out.println("Item Type: " + this.getItemType());
		System.out.println("Item Slot Type: " + this.getSlotType());
	}

}
