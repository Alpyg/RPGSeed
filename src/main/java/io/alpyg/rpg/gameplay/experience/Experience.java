package io.alpyg.rpg.gameplay.experience;

public abstract class Experience {
	
	private static int BASE_EXP = 10;
	private static double FACTOR = 2.2D;
	
	public static int getExperience(int level) {
		return (int) (BASE_EXP * Math.pow(level, FACTOR));
	}

	public static int getLevel(int experience) {
		return (int) Math.pow(( experience / BASE_EXP ), ( 1 / FACTOR ));
	}
	
	public static int getLevelUpExperience(int level) {
		return getExperience(level+1) - getExperience(level);
	}
	
}
