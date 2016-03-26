package nick.dev.items;

import java.util.HashMap;
import java.util.Map.Entry;

import nick.dev.entities.Player;

/**
 * This class will handle the Inventory used by the player in the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Inventory {

	public HashMap<String, BagItem> bagItems = new HashMap<String, BagItem>();

	public Inventory() {
		for (Entry<String, Item> entry : Item.getItemMap().entrySet()) {
			bagItems.put(entry.getKey(), new BagItem(Item.getItemMap().get(entry.getKey()), 10));
		}
	}

	/**
	 * This class allows multiple of the same Item in-game to be stored. This is
	 * used in the bagItems HashMap of the Inventory class to keep track of how
	 * many of a particular item the Player has.
	 * 
	 * @author acharles, nsanft
	 *
	 */
	private class BagItem {
		private Integer quantity;
		private Item item;

		public BagItem(Item item, Integer quantity) {
			this.item = item;
			this.quantity = quantity;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Item getItem() {
			return item;
		}
	}

	public String consumeItem(String itemID) {
		String responseMessage = "";
		Item item = Item.getItemMap().get(itemID);
		if (item.getItemType().equals("consumable")) {

			if (Player.getStats().getCurrentHP() + item.getHealing() >= Player.getStats().getMaxHP()) {
				responseMessage = "The " + item.getItemName() + " healed you for "
						+ (Player.getStats().getMaxHP() - Player.getStats().getCurrentHP()) + "!";
				Player.getStats().setCurrentHP(Player.getStats().getMaxHP());
			} else {
				Player.getStats().setCurrentHP(Player.getStats().getCurrentHP() + item.getHealing());
				responseMessage = "The " + item.getItemName() + " healed you for " + item.getHealing();
			}
			bagItems.get(itemID).setQuantity(bagItems.get(itemID).getQuantity() - 1);

			if (bagItems.get(itemID).getQuantity() == 0) {
				bagItems.remove(itemID);
			}
		} else {
			responseMessage = "You cannot consume: " + item.getItemName() + "!";
		}

		return responseMessage;
	}

	public void addItem(String itemID) {
		if (bagItems.containsKey(itemID)) {
			bagItems.get(itemID).setQuantity(bagItems.get(itemID).getQuantity() + 1);
		} else {
			bagItems.put(itemID, new BagItem(Item.getItemMap().get(itemID), 1));
		}
	}

	public HashMap<String, BagItem> getBagItems() {
		return bagItems;
	}

	public void printBagItems() {
		for (Entry<String, BagItem> entry : this.getBagItems().entrySet()) {

			Item item = entry.getValue().getItem();
			System.out.println("Item ID: " + entry.getKey());
			System.out.println("Item Name: " + item.getItemName());
			System.out.println("Item Quantity: " + entry.getValue().getQuantity());
			System.out.println("Item Healing: " + item.getHealing());
			System.out.println("Item Type: " + item.getItemType());
		}

	}
}
