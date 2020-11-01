package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Island Tiles 
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum TilesEnums {
	TWILIGHT_HOLLOW    ("Twilight Hollow"    ),
	BREAKERS_BRIDGE    ("Breakers Bridge"    ),
	CLIFFS_OF_ABANDON  ("Cliffs of Abandon"  ),
	CRIMSON_FOREST     ("Crimson Forest"     ),
	PHANTOM_ROCK       ("Phantom Rock"       ),
	WATCHTOWER         ("Watchtower"         ),
	DUNES_OF_DECEPTION ("Dunes of Deception" ),
	OBSERVATORY        ("Observatory"        ),
	MISTY_MARSH        ("Misty Marsh"        ),
	LOST_LAGOON        ("Lost Lagoon"        ),
	TIDAL_PALACE       ("Tidal Palace"       ), // Treasure Islands
	CORAL_PALACE       ("Coral Palace"       ),
	CAVE_OF_EMBERS     ("Cave of Embers"     ),
	CAVE_OF_SHADOWS    ("Cave of Shadows"    ),
	WHISPERING_GARDEN  ("Whispering Garden"  ),
	HOWLING_GARDEN     ("Howling Garden"     ),
	TEMPLE_OF_THE_SUN  ("Temple of the Sun"  ),
	TEMPLE_OF_THE_MOON ("Temple of the Moon" ),
	BRONZE_GATE        ("Bronze Gate"        ), // Engineer Initial Position
	COPPER_GATE        ("Copper Gate"        ), // Explorer Initial Position
	SILVER_GATE        ("Silver Gate"        ), // Messenger Initial Position
	GOLD_GATE          ("Gold Gate"          ), // Navigator Initial Position
	IRON_GATE          ("Iron Gate"          ), // Diver Initial Position
	FOOLS_LANDING      ("Fools' Landing"     ); // Pilot Initial Position

	private final String displayName;

	/**
	 * Constructor for tile enum
	 * @param s String which will be set to the display name of the tile.
	 */
    private TilesEnums(String s){
    	displayName = s; // String for name of IslandTile
    }

	/**
	 * Returns string representing the tile enum
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return this.displayName;
	}
}