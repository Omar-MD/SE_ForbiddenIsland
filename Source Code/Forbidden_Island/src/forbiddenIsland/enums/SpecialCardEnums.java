package forbiddenIsland.enums;

/**
 * Class holding the enumerated types for Special Cards in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public enum SpecialCardEnums {

	//-----------------------------------
    // Variables Setup
    //-----------------------------------
    HELICOPTER_LIFT ("Helicopter Lift"    ), 
    WATERS_RISE     ("Waters Rise"        ), 
    SANDBAGS        ("Sandbags"           );

	private final String displayName;

	//-----------------------------------
    // Constructor
    //-----------------------------------
	/**
	 * Constructor for Special Card enum.
	 * @param s String which will be set to the display name of the special card
	 */
    private SpecialCardEnums(String s){
    	displayName = s; // String for name of special card
	}
	
	//-----------------------------------
    // Methods
    //-----------------------------------
	/**
	 * Returns string representing the Special Card enum.
	 * @return string representation
	 */
    @Override
	public String toString() {
	    return displayName;
	}
}
