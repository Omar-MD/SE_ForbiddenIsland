package forbiddenIsland.gameplay;

import forbiddenIsland.enums.TreasureEnums;

/**
 * Public Treasure Class depicting treasure object in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Treasure {

	//----------------------------
	// Variable Setup
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
	// Methods 
	//----------------------------
    /**
     * Check if two treasures are equal.
     * @param o the reference object with which to compare
     * @return boolean
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
	 * Getter for the name of a Treasurer object.
	 * @return Treasure name enum	
	 */
	public TreasureEnums getTreasureName() {
		return this.treasureName;
	}

    /**
     * Returns treasures unique integer hashcode value.
     * @return unique integer value
     */
    @Override
	public int hashCode() {
		return treasureName.hashCode();
	}
}
