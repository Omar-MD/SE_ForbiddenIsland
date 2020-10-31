package enums;

// Enums class defining Island name constants
public enum IslandTileEnums {
	TWILIGHT_HOLLOW  	("Twilight Hollow"		),
	BREAKERS_BRIDGE  	("Breakers Bridge"		),
	COVES_OF_ABANDON 	("Coves Of Abandon"		),
	CRIMSON_FOREST   	("Crimson Forest"		),
	PHANTOM_ROCK	 	("Phantom Rock"			),
	WATCH_TOWER     	("Watch Tower"		  	),
	DUNES_OF_DECEPTION	("Dunes Of Deception"	),
	OBSERVATORY     	("Observatory"			),
	MISTY_MARCH			("Misty March"			),
	LOST_LAGOON			("Lost Lagoon"			),
	TIDAL_PALACE		("Tidal Palace"			), // Treasure Islands
	CORAL_PALACE		("Coral Palace"			),
	CAVE_OF_EMBERS		("Cave Of Embers"		),
	CAVE_OF_SHADOWS		("Cave Of Shadows"		),
	WHISPERING_GARDEN	("Whispering Garden"	),
	HOWLING_GARDEN		("Howling Garden"		),
	TEMPLE_OF_THE_SUN	("Temple Of The Sun"	),
	TEMPLE_OF_THE_MOON	("Temple Of The Moon"	),
	BRONZE_GATE			("Bronze Gate"			), // Engineer Initial Position
	COPPER_GATE			("Copper Gate"			), // Explorer  ...
	SILVER_GATE			("Silver Gate"			), // Messenger ...
	GOLD_GATE			("Gold Gate"			), // Navigator ...
	IRON_GATE			("Iron Gate"			), // Diver     ...
	FOOLS_LANDING		("Fools Landing"		); // Pilot 	...
	
	// Card Name
	private final String name;
	// Constructor
	private IslandTileEnums(String s) {
		this.name = s;
	}
	// Getter
	public String getName() {
		return this.name;
	}
}
