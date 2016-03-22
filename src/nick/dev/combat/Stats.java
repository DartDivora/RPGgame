package nick.dev.combat;

public class Stats {
	
	/*****************************************************
	 * Default constructor. Doesn't need to do anything 
	 * now since the stats are all initialized.
	 *****************************************************/
	public Stats() {}
	
	/*****************************************************
	 * Copy constructor. Copies an existing stats object 
	 * to create a new one. (may not need)
	 *****************************************************/
	public Stats(Stats copyFrom) {
		this.level = copyFrom.level;
		this.HP = copyFrom.HP;
		this.MP = copyFrom.MP;
		this.Strength = copyFrom.Strength;
		this.Defense = copyFrom.Defense;
		this.Intelligence = copyFrom.Intelligence;
		this.Dexterity = copyFrom.Dexterity;
		this.Speed = copyFrom.Speed;
	}
	
	/*****************************************************
	 * Prints out stats (debug)
	 *****************************************************/
	public void printStats() {
		System.out.println("Level, HP, MP, Str, Def, Int, Dex, Spd");
		System.out.print(this.level + " " + this.HP + " " + this.MP + " ");
		System.out.print(this.Strength + " " + this.Defense + " " + this.Intelligence + " ");
		System.out.print(this.Dexterity + " " + this.Speed + "\n");
	}
	
	/************************
	 * LEVEL
	 ************************/
	private Integer level = 1;
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
