package forbiddenIsland.gameplay;

import forbiddenIsland.enums.TreasureEnums;

/**
 * Public Treasure Class depicting treasure object
 * in the game of Forbidden Island
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Treasure {

	//----------------------------
	// Treasure Name variable
	//----------------------------
	private TreasureEnums treasureName;
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
	 * Constructor for a Treasure object.
	 * @param name	 Treasure name
	 */
	public Treasure(TreasureEnums name){
		this.treasureName=name;
	}

	//----------------------------
	// Getter 
	//----------------------------
	/**
	 * Getter for the name of a Treasurer object.
	 * @return name	 Treasure name
	 */
	public TreasureEnums getTreasureName() {
		return this.treasureName;
	}

    /**
     * Returns boolean to check if treasure are equal
     * @return the boolean
     */
    @Override
	public boolean equals(Object o) {
		// Check if o is an instance of Treasure
		if(o instanceof Treasure) {
			// Typecast o to Treasure so that we can compare data members
			Treasure p = (Treasure) o;
			// Compare the data members and return accordingly
			return treasureName.equals(p.getTreasureName());
		}
		return false;
	}

    /**
     * Returns unique integer hashcode value
     * @return unique integer value
     */
    @Override
	public int hashCode() {
		return treasureName.hashCode();
	}
}
