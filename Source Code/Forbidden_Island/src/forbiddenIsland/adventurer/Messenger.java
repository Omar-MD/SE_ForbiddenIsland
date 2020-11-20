package forbiddenIsland.adventurer;

/**
 * Class depicting adventurer type Messenger
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Messenger extends Adventurer {
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for a Messenger adventurer role.
     */
	public Messenger() {
		super();
	}

	//----------------------------
	// Methods
	//----------------------------
	@Override 
	/**
	 * Print Messenger role 
	 * @return String
	 */
	public String toString() {
		return "Messenger";
	}
}
