package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Tile State
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum StateEnums {
	DRY     ("Dry"     ,'X'),
	FLOODED ("Flooded" ,'~'),
	SUNK    ("Sunk"    ,' ');

	private final String displayName;
	private final char mapChar;

	/**
	 * Constructor for tile state enum
	 * @param s String which will be set to the display name of the tile state.
	 * @param c Character associated with tile state to be printed on Board
	 */
    private StateEnums(String s, char c){
    	displayName = s; // String for state of IslandTile
    	mapChar = c; // char for default representation of tile state on Board
    }

    /**
     * gets character relating to a Tile state
     * @return the character.
     */
    public char getChar() {
        return mapChar;
    }

	/**
	 * Returns string representing the tile state enum
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return displayName;
	}
}
