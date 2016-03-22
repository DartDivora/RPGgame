package nick.dev.combat;

public class Stats {
	
	/************************
	 * LEVEL
	 ************************/
	private Integer level;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/************************
	 * HP
	 ************************/
	private Integer HP = 10;
	public Integer getHP() {
		return HP;
	}
	public void setHP(Integer hp) {
		this.HP = hp;
	}
	
	/************************
	 * MP
	 ************************/
	private Integer MP = 0;
	public Integer getMP() {
		return MP;
	}
	public void setMP(Integer mP) {
		this.MP = mP;
	}
	
	/************************
	 * STRENGTH
	 ************************/
	private Integer Strength = 1;
	public Integer getStrength() {
		return Strength;
	}
	public void setStrength(Integer strength) {
		this.Strength = strength;
	}
	
	/************************
	 * DEFENSE
	 ************************/
	private Integer Defense = 1;
	public Integer getDefense() {
		return Defense;
	}
	public void setDefense(Integer defense) {
		this.Defense = defense;
	}
	
	/************************
	 * INTELLIGENCE
	 ************************/
	private Integer Intelligence = 1;
	public Integer getIntelligence() {
		return Intelligence;
	}
	public void setIntelligence(Integer intelligence) {
		this.Intelligence = intelligence;
	}
	
	/************************
	 * DEXTERITY
	 ************************/
	private Integer Dexterity = 1;
	public Integer getDexterity() {
		return Dexterity;
	}
	public void setDexterity(Integer dexterity) {
		this.Dexterity = dexterity;
	}
	
	/************************
	 * SPEED
	 ************************/
	private Integer Speed = 3;
	public Integer getSpeed() {
		return Speed;
	}
	public void setSpeed(Integer speed) {
		this.Speed = speed;
	}
}
