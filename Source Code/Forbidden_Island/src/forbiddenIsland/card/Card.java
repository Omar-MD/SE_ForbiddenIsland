package forbiddenIsland.card;

/**
 * Abstract class depicting a Card object.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public abstract class Card{

    //-----------------------------------
    // Variable Setup
    //-----------------------------------
	@SuppressWarnings("rawtypes")
	private Enum name;
	
	//-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor for a Card object.
     * @param cardName  Name of the Card
     */
	@SuppressWarnings("rawtypes")
	Card(Enum cardName){
		this.name = cardName;
	}
	
    //-----------------------------------
    // Getters and Setters
    //-----------------------------------
    /**
     * Returns the Card name enum.
     * @return Card name enum 
     */
	@SuppressWarnings("rawtypes")
	public Enum getName(){
		return this.name;
    }
    
	 /**
     * Sets the Card name.
     * @param cardName  Card name
     */
	@SuppressWarnings("rawtypes")
	public void setName(Enum cardName) {
        this.name = cardName;
    }

	@Override
	/**
	 * Print Card enum name.
	 * @return String containing Card enum
	 */
	public String toString() {
		return getName().toString();
	}
}
