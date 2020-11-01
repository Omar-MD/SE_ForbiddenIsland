package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Treasures
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum TreasureEnums {
	THE_EARTH_STONE        ("The Earth Stone"        ),
	THE_STATUE_OF_THE_WIND ("The Statue of the Wind" ),
	THE_CRYSTAL_OF_FIRE    ("The Crystal of Fire"    ),
	THE_OCEANS_CHALICE     ("The Ocean's Chalice"    );
	private final String displayName;

	/**
	 * Constructor for treasure enum
	 * @param s String which will be set to the display name of the treasure.
	 */
    private TreasureEnums(String s){
    	displayName = s; // String for name of treasure
    }

	/**
	 * Returns string representing the treasure enum
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return this.displayName;
	}
}
