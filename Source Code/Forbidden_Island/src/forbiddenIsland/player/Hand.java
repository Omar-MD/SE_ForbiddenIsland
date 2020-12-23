package forbiddenIsland.player;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import forbiddenIsland.card.Card;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.enums.SpecialCardEnums;

/**
 * Player Hand class in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class Hand {
	
	//---------------------------
	// Variable Setup
    //---------------------------
	private List<Card> handDeck;

	//---------------------------
	// Constructor
	//---------------------------
	/**
	 * Constructor for a Player Hand deck.
	 */
	public Hand(){
		this.handDeck = new ArrayList<Card>();
	}
	
	//---------------------------
	// Methods
	//---------------------------
	/**
	 * Method to discard used treasure Cards to treasure Discard pile.
	 */
	public void discardTreasureSet(){
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		for (TreasureCard tCard:getTreasureCardSet()) {
			treasureDeck.discard(tCard);
			handDeck.remove(tCard);
		}
	}

	/**
	 * Verify Deck consists of a full matching treasure card.
	 * @return boolean
	 */
	public boolean isTreasureDeck() {
		if(getTreasureCardSet().size() == 4)
			return true;
		return false;
	}

	/**
	 * Checks if the player hand is empty.
	 * @return boolean
	 */
	public boolean isEmpty() {
		if(handDeck.isEmpty())
			return true;
		return false;
	}

	/**
	 * Check if Special Card in hand.
	 * @param cardName SpecialCard Enum 
	 * @return boolean 
	 */
	public boolean hasSpecialCard(SpecialCardEnums cardName){
		for(Card card:getDeck()){
			if(card instanceof SpecialCard){
				SpecialCard specialCard = (SpecialCard) card;
				if(specialCard.getName().equals(cardName)) {
					return true;
				}
			}
		}
		return false;
	}

	//---------------------------
	// Getters
	//---------------------------
	/**
	 * Return  Hand Deck.
	 * @return handDeck
	 */
	public List<Card> getDeck(){
		return this.handDeck;
	}

	/**
	 * Return full set of matched treasure cards, else last matched set.
	 * @return Treasure card set 
	 */
	public List<TreasureCard> getTreasureCardSet(){
		List<TreasureCard> matchedSet = new ArrayList<TreasureCard>();
		HashSet<TreasureCard> uniqueCards = new HashSet<TreasureCard>();
		
		// Find unique treasure cards in deck
		for(Card c:getDeck()){
			if(c instanceof TreasureCard){
				uniqueCards.add((TreasureCard) c);
			}
		}

		// Find full matched treasure set if in deck
		// Otherwise return last matched set
		for(TreasureCard uC:uniqueCards){
			matchedSet.clear();
			for(Card c:getDeck()){
				if(c instanceof TreasureCard){
					TreasureCard matchedCard = (TreasureCard) c;
					if(matchedCard.equals(uC))
						matchedSet.add(matchedCard);
				}
				if(matchedSet.size() == 4) return matchedSet;
			}
		}
		return matchedSet;
	}

	/**
	 * Return list of Special Cards in player hand.
	 * @return List of Special Cards
	 */
	public List<Card> getSpecialCards() {
		List<Card> sCards = new ArrayList<Card>();
		for(int i=0; i < handDeck.size(); i++){
			if(getCard(i) instanceof SpecialCard){
				sCards.add(getCard(i));
			}
		}
		return sCards;
	}

	/**
	 * Return list of Treasure Cards in player hand.
	 * @return List of Treasure Cards
	 */
	public List<Card> getTreasureCards() {
		List<Card> tCards = new ArrayList<Card>();
		for(int i=0; i < handDeck.size(); i++){
			if(getCard(i) instanceof TreasureCard){
				tCards.add(getCard(i));
			}
		}
		return tCards;
	}

	/**
	 * Return Name of Treasure to be captured if player hand consists of full treasure deck.
	 * @return Treasure name enum or null
	 */
	public TreasureEnums getTreasureName() {
		for(TreasureCard c:getTreasureCardSet()){
			if(isTreasureDeck())
				return (TreasureEnums) c.getName();
		}
		return null;
	}

	/**
	 * Return Card in hand.
	 * @param index	Card index
	 * @return Card
	 */
	public Card getCard(int index) {
		return this.handDeck.get(index);
	}

	/**
	 * Return 1st index of a given SpecialCard in hand.
	 * @param cardName SpecialCard Enum 
	 * @return int	   Special Card integer index
	 */
	public int getIndex(SpecialCardEnums cardName){
		boolean hasCard=false;
		int i=0;
		while(!hasCard){
			if(getCard(i).getName().equals(cardName))
				hasCard=true;
			else
				i+=1;
		}
		return i;
	}

	//---------------------------
	// Setter
	//---------------------------
	/**
	 * Adds new Player card to hand deck.
	 * @param card	New card to hand
	 */
	public void addCard(Card card) {
		this.handDeck.add(card);
	}

	/**
	 * Print List of Cards from Player Hand.
	 * @return String containing List of Cards from Player Hand
	 */
	public String printCards(List<Card> listCards) {
		List<String> cards = new ArrayList<String>();
		for(int i=1; i < listCards.size()+1; i++){
			 cards.add("\n["+ i +"]"+" : " + listCards.get(i-1).toString());
		}
		return String.join(" ", cards);
	}

	@Override
	/**
	 * Print Player Hand 
	 * @return String containing Player Hand
	 */
	public String toString() {
		return printCards(handDeck);
	}
}
