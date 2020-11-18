package forbiddenIsland.card;

/**
 * Abstract class depicting a Card object.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public abstract class Card<E>{

    //-----------------------------------
    // Variable Setup
    //-----------------------------------
	private E name;
	
	//-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor for a Card object.
     * @param cardName The name of the Card
     */
	Card(E cardName){
		this.name = cardName;
	}
	
    //-----------------------------------
    // Getters and Setters
    //-----------------------------------
    /**
     * Returns the Card name
     * @return the card name 
     */
	public E getName(){
		return this.name;
    }
    
	 /**
     * Sets the Card name
     * @param cardName the Card name
     */
	public void setName(E cardName) {
        this.name = cardName;
    }
}
