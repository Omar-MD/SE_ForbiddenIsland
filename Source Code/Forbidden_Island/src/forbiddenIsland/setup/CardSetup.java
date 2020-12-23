package forbiddenIsland.setup;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.FloodDeck;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * Class to handle all aspects of setting up the Cards for a game of Forbidden Island.
 * This class should only exist within the Setup facade.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */

public class CardSetup {

	//-----------------------------------
	// Variables Setup
	//-----------------------------------
	private FloodDeck floodDeck;
	private TreasureDeck treasureDeck;

	//-----------------------------------
	// Constructor
	//-----------------------------------
	/**
	 * Constructor for the CardSetup class
	 */
	public CardSetup() {
		this.floodDeck = FloodDeck.getInstance();
		this.treasureDeck = TreasureDeck.getInstance();
	}

	//-----------------------------------
	// Methods
	//-----------------------------------
	/**
	 * Shuffle the Flood Deck and draw the top 6 cards. 
	 * For each card drawn, flip the Island Tile to its flooded state (DRY by default).
	 * Each drawn card is placed into the Flood discard pile.
	 */
	public void drawFloodCards() {
		// Get instance of board
		Board board = Board.getInstance();
		floodDeck.shuffleDeck(); // Shuffle Flood deck
		// Draw the top 6 cards
		for (int i = 0; i < 6; i++) {
			// Draw the top card and place it in the discard pile
			Card c = floodDeck.drawCard();
			IslandTile tile = board.getIslandTile((TilesEnums) c.getName());
			// Set tile state to flooded.
			tile.setState(StateEnums.FLOODED);
		}
  	}

	/**
	 * Shuffle the Treasure Deck and assign 2 cards to each player.
	 * If Waters Rise card is drawn, place it back in the deck and shuffle.
	 */
	public void drawTreasureCards() {
		// Get instance of PlayerList
		PlayerList setupPlayers = PlayerList.getInstance();
		treasureDeck.shuffleDeck(); // Shuffle Treasure deck

        for (Player p:setupPlayers.getAllPlayers()) {
        	int i = 0;
        	while (i < 2) {
            	// Draw the top card
            	Card c = treasureDeck.drawCard();
            	if (c instanceof SpecialCard) {
            		SpecialCardEnums sEnum = (SpecialCardEnums) c.getName();
            		if (sEnum.equals(SpecialCardEnums.WATERS_RISE)) {
            			System.out.println("Waters Rise card is drawn.");
            			// We will discard the card and place it back into the Treasure deck,
            			// which is then shuffled
            			treasureDeck.discard(c);
            			treasureDeck.refillDeck();
            		}
            		else {
                		// Assign the treasure card to the player
                    	p.getHand().addCard(c);
                    	i++;
                	}
            	}
            	else {
            		// Assign the treasure card to the player
                	p.getHand().addCard(c);
                	i++;
            	}
            }
        }
  	}
}
