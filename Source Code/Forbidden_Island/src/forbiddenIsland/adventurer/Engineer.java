package forbiddenIsland.adventurer;

/**
 * Class depicting adventurer type Engineer in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Engineer extends Adventurer {
	
	//-----------------------------------
	// Constructor
	//-----------------------------------
	/**
     * Constructor for an Engineer adventurer role.
     */
	public Engineer() {
		super();
	}

	//-----------------------------------
	// Methods
	//-----------------------------------
	@Override 
	/**
	 * Print Engineer role.
	 * @return String
	 */
	public String toString() {
		return "Engineer";
	}
}
