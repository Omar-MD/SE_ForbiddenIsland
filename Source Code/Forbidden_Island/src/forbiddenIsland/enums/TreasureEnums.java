package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Treasures
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum TreasureEnums {
	THE_EARTH_STONE        ("The Earth Stone"        ,'£'),
	THE_STATUE_OF_THE_WIND ("The Statue of the Wind" ,'$'),
	THE_CRYSTAL_OF_FIRE    ("The Crystal of Fire"    ,'#'),
	THE_OCEANS_CHALICE     ("The Ocean's Chalice"    ,'@');

	private final String displayName;
	private final char mapChar;

	/**
	 * Constructor for treasure enum
	 * @param s String which will be set to the display name of the treasure.
	 * @param c Character associated with treasure to be printed on Board
	 */
    private TreasureEnums(String s, char c){
    	displayName = s; // String for name of treasure
    	mapChar = c; // char for default representation of treasure on Board
    }

    /**
     * gets character relating to a Treasure
     * @return the character.
     */
    public char getChar() {
        return mapChar;
    }

	/**
	 * Returns string representing the treasure enum
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return displayName;
	}
}
