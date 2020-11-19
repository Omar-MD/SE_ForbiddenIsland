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
	 * @param name.	 Treasure name
	 */
	public Treasure(TreasureEnums name){
		this.treasureName=name;
	}

	//----------------------------
	// Getter 
	//----------------------------
	/**
	 * Getter for the name of a Treasurer object.
	 * @return name. Treasure name
	 */
	public TreasureEnums getTreasureName() {
		return this.treasureName;
	}
}
