// GameConstants Class
// This Class defines the Constants used in the Game.

public class GameConstants {

	public static final int MAX_PLAYER = 5;
	public static final int NUM_OF_CATE = 5;

	// Each Category Number
	public static final int HARDNESS = 0;
	public static final int SPECIFIC_GRAVITY = 1;
	public static final int CLEAVAGE = 2;
	public static final int CRUSTAL_ABUNDANCE = 3;
	public static final int ECONOMIC_VALUE = 4;

	public static final int THE_GEOLOGIST = 6;	// Pick Category
	public static final String[] CATEGORY = {"Hardness","Specific Gravity","Cleavage","Crustal Abundance", "Economic Value"};

	// Main _ compareWithPrevCard()
	public static final String[] CLEAVAGE_VALUES = new String[] {"none", "poor/none","1 poor","2 poor","1 good/1 poor","2 good","3 good","1 perfect","1 perfect/1 good","1 perfect/2 good","2 perfect/1 good","3 perfect","4 perfect","6 perfect"};
	public static final String[] CRUSTALABUNDANCE_VALUES = new String[] {"ultratrace","trace","low","moderate","high","very high"};
	public static final String[] ECONOMIC_VALUES = new String[] {"trivial","low","moderate","high","very high","I'm rich"};

} // GameConstants Class
