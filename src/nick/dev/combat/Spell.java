package nick.dev.combat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nick.dev.gfx.Animation;
import nick.dev.utilities.Utilities;

public class Spell {

	private static HashMap<String, Spell> spellMap = new HashMap<String, Spell>();
	private static HashMap<String, Method> spellAction;
	private String spellName;
	private Integer damage;
	private Integer healing;
	@SuppressWarnings("unused")
	private Boolean combatSpell, fieldSpell;

	private Animation spellAnimation;

	static {

		Gson gson = new Gson();
		String JSONString = Utilities.getStringFromFile(Utilities.getPropValue("spellJSON"));
		spellMap = gson.fromJson(JSONString, new TypeToken<HashMap<String, Spell>>() {}.getType());
		spellAction = new HashMap<String, Method>();
		for (Entry<String, Spell> entry : spellMap.entrySet()) {
			try {
				@SuppressWarnings("rawtypes")
				Class[] params = new Class[2];
				params[0] = Stats.class;
				params[1] = Stats.class;
				spellAction.put(entry.getValue().getSpellName(),Spell.class.getMethod(entry.getValue().getSpellName(), params));
			} catch (NoSuchMethodException | SecurityException e) {
				System.out.println("No method exists for: " + entry.getValue().getSpellName());
				e.printStackTrace();
			}
			entry.getValue().initialize();
			System.out.println(entry.getValue().getSpellName());
		}

		System.out.println(spellMap.entrySet());
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

	public Integer heal(Stats source, Stats target) {
		this.healing = healing + source.getIntelligence();
		return healing;
	}

	public void warp(Stats source, Stats target) {
	}

}
