package forbiddenIsland.card;

import java.util.Collections;
import java.util.Stack;

import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.TreasureEnums;

/**
 * Singleton Class for the Treasure Deck in a game of Forbidden Island. 
 * Can initialise a deck, refill the deck, draw treasure cards and discard them.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class TreasureDeck {

    //-------------------------------------------
    // Variable Setup
    //-------------------------------------------
	private static TreasureDeck theDeck;
	private Stack<Card>         cardsInDeck;
	private Stack<Card>         discardPile;

    //-------------------------------------------
    // Get Instance of Singleton
    //-------------------------------------------
	/**
	 * gets the singleton instance of the TreasureDeck object.
	 * @return  singleton TreasureDeck object
	 */
	public static TreasureDeck getInstance(){
        if(theDeck == null){
            theDeck = new TreasureDeck();
        }
        return theDeck;
    }

    //-------------------------------------------
    // Constructor
    //-------------------------------------------
	/**
	 * Generates all the cards in the Treasure Deck.
	 */
	private TreasureDeck() {
		// Initialise Deck and Discard Pile with empty stack of Cards
	    this.cardsInDeck = new Stack<Card>();
	    this.discardPile = new Stack<Card>();
	    initTreasureDeck();
	}

    //-------------------------------------------
    // Methods
    //-------------------------------------------
	/**
     * Initialise the Treasure Deck.
     */
    private void initTreasureDeck() {
    	// Treasure Deck contains 20 Treasure Cards each associated with a Treasure
	    for (TreasureEnums treasure : TreasureEnums.values()) {
	    	// There are 5 cards for each treasure
	    	for (int i = 0; i < 5; i++) {
	    		cardsInDeck.push(new TreasureCard(treasure));
	    	}
    	}
	    // There are 3 Helicopter Lift Cards
    	for (int i = 0; i < 3; i++) {
    		cardsInDeck.push(new SpecialCard(SpecialCardEnums.HELICOPTER_LIFT));
    	}
    	// There are 2 Sandbags Cards
    	for (int i = 0; i < 2; i++) {
    		cardsInDeck.push(new SpecialCard(SpecialCardEnums.SANDBAGS));
    	}
    	// There are 3 Waters Rise Cards
    	for (int i = 0; i < 3; i++) {
    		cardsInDeck.push(new SpecialCard(SpecialCardEnums.WATERS_RISE));
    	}
    }

    /**
	 * Shuffle the Treasure Deck.
	 */
	public void shuffleDeck() {
		System.out.println("Shuffling the Treasure Deck.");
		Collections.shuffle(cardsInDeck);
	}

	/**
	 * Place the cards in the Treasure Discard Pile back in the Treasure Deck and shuffle it.
	 */
	public void refillDeck() {
		System.out.println("Adding the discard pile back into the Treasure Deck.");
		while(!discardPile.isEmpty()){
		    cardsInDeck.push(discardPile.pop());
		}
		shuffleDeck();
	}

	/**
	 * Draw the top card from the Treasure Deck.
	 * @return The drawn Card
	 */
	public Card drawCard() {
		if (cardsInDeck.isEmpty()) {
			refillDeck();
		}
		// Draw the top card
		Card drawnCard = cardsInDeck.pop(); // Pop from deck
		return drawnCard;
	}

	/**
	 * Discard the input card.
	 * @param c    input Card
	 */
	public void discard(Card c) {
		discardPile.push(c);
	}

	//----------------------------------------
    // Getters
    //----------------------------------------
    /**
     * returns the Treasure deck.
     * @return     Treasure deck
     */
    public Stack<Card> getDeck() {
        return cardsInDeck;
    }

    /**
     * returns the Treasure discard pile.
     * @return     Treasure discard pile
     */
    public Stack<Card> getDiscardPile() {
        return discardPile;
    }

    //-------------------------------------------
    // Singleton destroyers for Unit-Testing only
    //-------------------------------------------

	public void destroyMe() {
	    theDeck = null;
	}
}
