package nick.dev.combat.spell;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.combat.Stats;
import nick.dev.gfx.Animation;
import nick.dev.utilities.Utilities;

public class Spell {

	private static HashMap<String, Spell> spellMap;
	private String spellName;
	private Integer damage;
	private Boolean combatSpell, fieldSpell;

	private Animation spellAnimation;

	static {
		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("spellJSON"));

		spellMap = gson.fromJson(JSONString, new TypeToken<HashMap<String, Spell>>() {
		}.getType());

		for (Entry<String, Spell> entry : spellMap.entrySet()) {
			entry.getValue().initialize();
			System.out.println(entry.getValue().getSpellName());
		}

		System.out.println(spellMap.entrySet());
	}

	public Spell() {
		spellMap = new HashMap<String, Spell>();
	}

	public void initialize() {

	}

	public static HashMap<String, Spell> getSpellMap() {
		return spellMap;
	}

	public Animation getSpellAnimation() {
		return spellAnimation;
	}

	public String getSpellName() {
		return spellName;
	}

	public Integer fireball(Stats source, Stats target) {
		damage = damage + source.getIntelligence();

		return damage;
	}

	public Integer ice(Stats source, Stats target) {
		damage = damage + source.getIntelligence();
		return damage;
	}

}
