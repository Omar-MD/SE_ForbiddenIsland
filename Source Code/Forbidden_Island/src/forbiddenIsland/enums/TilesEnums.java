package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Island Tiles 
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum TilesEnums {
	TWILIGHT_HOLLOW    ("Twilight Hollow"    ,"TWH"),
	BREAKERS_BRIDGE    ("Breakers Bridge"    ,"BRB"),
	CLIFFS_OF_ABANDON  ("Cliffs of Abandon"  ,"COA"),
	CRIMSON_FOREST     ("Crimson Forest"     ,"CRF"),
	PHANTOM_ROCK       ("Phantom Rock"       ,"PHR"),
	WATCHTOWER         ("Watchtower"         ,"WAT"),
	DUNES_OF_DECEPTION ("Dunes of Deception" ,"DOD"),
	OBSERVATORY        ("Observatory"        ,"OBS"),
	MISTY_MARSH        ("Misty Marsh"        ,"MIM"),
	LOST_LAGOON        ("Lost Lagoon"        ,"LOL"),
	TIDAL_PALACE       ("Tidal Palace"       ,"TIP"), // Treasure Islands
	CORAL_PALACE       ("Coral Palace"       ,"COP"),
	CAVE_OF_EMBERS     ("Cave of Embers"     ,"COE"),
	CAVE_OF_SHADOWS    ("Cave of Shadows"    ,"COS"),
	WHISPERING_GARDEN  ("Whispering Garden"  ,"WHG"),
	HOWLING_GARDEN     ("Howling Garden"     ,"HOG"),
	TEMPLE_OF_THE_SUN  ("Temple of the Sun"  ,"TOS"),
	TEMPLE_OF_THE_MOON ("Temple of the Moon" ,"TOM"),
	BRONZE_GATE        ("Bronze Gate"        ,"BRG"), // Engineer Initial Position
	COPPER_GATE        ("Copper Gate"        ,"COG"), // Explorer Initial Position
	SILVER_GATE        ("Silver Gate"        ,"SIG"), // Messenger Initial Position
	GOLD_GATE          ("Gold Gate"          ,"GOG"), // Navigator Initial Position
	IRON_GATE          ("Iron Gate"          ,"IRG"), // Diver Initial Position
	FOOLS_LANDING      ("Fools' Landing"     ,"FOL"); // Pilot Initial Position

	private final String displayName;
	private final String mapString;
	/**
	 * Constructor for tile enum
	 * @param s String which will be set to the display name of the tile.
	 * @param m String associated with tile to be printed on Board
	 */
    private TilesEnums(String s, String m){
    	displayName = s; // String for name of Island Tile
    	mapString = m; // String for default representation of Island Tile on Board
    }

    /**
     * gets String relating to the Tile representation on the Board
     * @return the string.
     */
    public String getMapString() {
        return mapString;
    }

	/**
	 * Returns string representing the tile enum
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return displayName;
	}
}