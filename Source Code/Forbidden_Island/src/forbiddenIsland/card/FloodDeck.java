package forbiddenIsland.card;

import java.util.Collections;
import java.util.Stack;

import forbiddenIsland.enums.TilesEnums;

/**
 * Singleton Class for the Deck of Flood Cards in a game
 * of Forbidden Island. Can initialise a deck, refill the deck
 * and draw flood cards.
 *
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */

public class FloodDeck {

	//===========================================================
	// Variable Setup
	//===========================================================
	private static FloodDeck theDeck;
	private Stack<Card>      cardsInDeck;
	private Stack<Card>      discardPile;

	//===========================================================
	// Get Instance of Singleton
	//===========================================================
	/**
	 * gets the singleton instance of the FloodDeck object.
	 * @return the single FloodDeck object.
	 */
	public static FloodDeck getInstance(){
        if(theDeck == null){
            theDeck = new FloodDeck();
        }
        return theDeck;
    }

	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Generates all the cards in the Flood Deck.
	 */
	private FloodDeck() {
	    // Initialise Deck and Discard Pile with empty stack of Cards
	    this.cardsInDeck = new Stack<Card>();
	    this.discardPile = new Stack<Card>();
	    initFloodDeck();
	}

	//===========================================================
	// Other Methods
	//===========================================================
	/**
     * Initialise the Flood Deck
     */
    private void initFloodDeck() {
    	// Each card corresponds to an Island Tile
	    for (TilesEnums tile : TilesEnums.values()) {
	    	cardsInDeck.push(new FloodCard(tile));
    	}
    }

    /**
	 * Shuffle the Flood Deck
	 */
	public void shuffleDeck() {
		System.out.println("Shuffling the Flood Deck.");
		Collections.shuffle(cardsInDeck);
	}

	/**
	 * Place the cards in the Flood Discard Pile
	 * back in the Flood Deck and shuffle it
	 */
	public void refillDeck() {
		System.out.println("Adding the discard pile back into the Flood Deck.");
		while(!discardPile.isEmpty()){
		    cardsInDeck.push(discardPile.pop());
		}
		shuffleDeck();
	}

	/**
	 * Draw the top card from the Flood Deck and
	 * place it in the Flood discard pile.
	 * @return The drawn Card
	 */
	public Card drawCard() {
		if (cardsInDeck.isEmpty()) {
			refillDeck();
		}
		// Draw the top card
		Card drawnCard = cardsInDeck.pop(); // Pop from deck
		discardPile.push(drawnCard);
		return drawnCard;
	}

	//-----------------------------------
    // Getters
    //-----------------------------------
    /**
     * returns the Flood deck.
     * @return the Flood deck.
     */
    public Stack<Card> getDeck() {
        return cardsInDeck;
    }

    /**
     * returns the Flood discard pile.
     * @return the Flood discard pile.
     */
    public Stack<Card> getDiscardPile() {
        return discardPile;
    }

	//===========================================================
	// Singleton destroyer for unit testing ONLY
	//===========================================================

	public void destroyMe() {
	    theDeck = null;
	}
}
