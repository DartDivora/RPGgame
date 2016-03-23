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
		this.MaxHP = copyFrom.MaxHP;
		this.currentHP = copyFrom.currentHP;
		this.MaxMP = copyFrom.MaxMP;
		this.currentMP = copyFrom.currentMP;
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
		System.out.print(this.level + " " + this.MaxHP + " " + this.MaxMP + " ");
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
	 * CURRENT EXP
	 ************************/
	private Integer Exp = 1;
	public Integer getCurrentExp() {
		return Exp;
	}
	public void setCurrentExp(Integer exp) {
		this.Exp = exp;
	}
	
	/************************
	 * MAX HP
	 ************************/
	private Integer MaxHP = 10;
	public Integer getMaxHP() {
		return MaxHP;
	}
	public void setMaxHP(Integer hp) {
		this.MaxHP = hp;
	}
	
	/************************
	 * CURRENT HP
	 ************************/
	private Integer currentHP = 10;
	public Integer getCurrentHP() {
		return this.currentHP;
	}
	public void setCurrentHP(Integer hp) {
		this.currentHP = hp;
	}
	
	/************************
	 * MAX MP
	 ************************/
	private Integer MaxMP = 0;
	public Integer getMaxMP() {
		return MaxMP;
	}
	public void setMaxMP(Integer mP) {
		this.MaxMP = mP;
	}
	
	/************************
	 * CURRENT MP
	 ************************/
	private Integer currentMP = 10;
	public Integer getCurrentMP() {
		return this.currentHP;
	}
	public void setCurrentMP(Integer mp) {
		this.currentHP = mp;
	}
	
	/************************
	 * VITALITY
	 ************************/
	private Integer Vitality = 0;
	public Integer getVitality() {
		return Vitality;
	}
	public void setVitality(Integer vitality) {
		this.Vitality = vitality;
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
	 * WISDOM
	 ************************/
	private Integer Wisdom = 1;
	public Integer getWisdom() {
		return Wisdom;
	}
	public void setWisdom(Integer wisdom) {
		this.Wisdom = wisdom;
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
	 * LUCK
	 ************************/
	private Integer Luck = 1;
	public Integer getLuck() {
		return Luck;
	}
	public void setLuck(Integer luck) {
		this.Luck = luck;
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
