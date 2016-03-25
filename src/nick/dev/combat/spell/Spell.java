package nick.dev.combat.spell;

import java.util.HashMap;

import nick.dev.combat.Stats;
import nick.dev.gfx.Animation;

public class Spell {

	private static HashMap<Integer, Spell> spellMap;
	private Integer damage;
	private Boolean combatSpell, fieldSpell;
	private Animation spellAnimation;

	public static HashMap<Integer, Spell> getSpellMap() {
		return spellMap;
	}

	public Animation getSpellAnimation() {
		return spellAnimation;
	}

	public Spell() {
		spellMap = new HashMap<Integer, Spell>();
	}

	public void combatAction(Stats source, Stats target) {

	}

	public void fieldAction(Stats source) {

	}

}
