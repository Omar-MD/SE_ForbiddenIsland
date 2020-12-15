package forbiddenIsland.card;

import forbiddenIsland.enums.TreasureEnums;

/**
 * TreasureCard class depicting a Treasure Card object 
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class TreasureCard extends Card{
    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor for a TreasureCard object.
     * @param cardName The name of the Card
     */
	public TreasureCard(TreasureEnums cardName){
		super(cardName);
	}

	/**
     * Returns boolean to check if treasure cards are the same
     * @return the boolean
     */
    @Override
	public boolean equals(Object o) {
		// Check if o is an instance of Treasure
		if(o instanceof TreasureCard) {
			// Typecast o to Treasure so that we can compare data members
			TreasureCard p = (TreasureCard) o;
			// Compare the data members and return accordingly
			return getName().equals(p.getName());
		}
		return false;
	}

    /**
     * Returns unique integer hashcode value
     * @return unique integer value
     */
    @Override
	public int hashCode() {
		return getName().hashCode();
	}
}
