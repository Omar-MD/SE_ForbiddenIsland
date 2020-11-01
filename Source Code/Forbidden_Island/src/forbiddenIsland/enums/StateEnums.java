package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Tile State
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum StateEnums {
	DRY     ("Dry"     ),
	FLOODED ("Flooded" ),
	SUNK    ("Sunk"    );

	private final String displayName;

	/**
	 * Constructor for tile state enum
	 * @param s String which will be set to the display name of the tile state.
	 */
    private StateEnums(String s){
    	displayName = s; // String for state of IslandTile
    }

	/**
	 * Returns string representing the tile state enum
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return this.displayName;
	}
}
