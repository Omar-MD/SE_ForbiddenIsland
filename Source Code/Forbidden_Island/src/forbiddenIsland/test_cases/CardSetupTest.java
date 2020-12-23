package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.card.FloodDeck;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;
import forbiddenIsland.setup.CardSetup;

/**
 * JUnit test cases for the CardSetup class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class CardSetupTest {

	private FloodDeck floodDeck;
	private TreasureDeck treasureDeck;

	@BeforeEach
	public void setUp() throws Exception {
		floodDeck = FloodDeck.getInstance();
		treasureDeck = TreasureDeck.getInstance();
	}

	@AfterEach
	public void tearDown() throws Exception {
		floodDeck.destroyMe();
		treasureDeck.destroyMe();
	}

	@Test
	public void testDrawFloodCards() {
		// Flood Deck is full at the beginning and Discard Pile is empty
		assertEquals(24,floodDeck.getDeck().size(), "Checking if deck contains the 24 flood cards");
		assertEquals(0,floodDeck.getDiscardPile().size(), "Checking if discard pile is empty");

		// Perform Card Setup of Flood Cards
		CardSetup cardSetup = new CardSetup();
		cardSetup.drawFloodCards();

		// Flood Deck will now be updated
		assertEquals(18,floodDeck.getDeck().size(), "Checking if deck contains 6 less cards after drawing flood cards");
		assertEquals(6,floodDeck.getDiscardPile().size(), "Checking if discard pile contains 6 new cards");

	}

	@Test
	public void testDrawTreasureCards() {
		// Creating two random players
		PlayerList playerList = PlayerList.getInstance();
		Player p1 = new Player(1,"John","Pilot");
		Player p2 = new Player(2,"James","Messenger");
		playerList.addPlayer(p1);
		playerList.addPlayer(p2);

		// Treasure Deck is full at the beginning and Player hand is empty
		assertEquals(28,treasureDeck.getDeck().size(), "Checking if deck contains the 28 treasure deck cards");
		assertEquals(0,p1.getHand().getDeck().size(), "Checking if p1 hand is empty");
		assertEquals(0,p2.getHand().getDeck().size(), "Checking if p2 hand is empty");

		// Perform Card Setup of Treasure Cards
		CardSetup cardSetup = new CardSetup();
		cardSetup.drawTreasureCards();

		// Treasure Deck and Player hand should now be updated
		assertEquals(24,treasureDeck.getDeck().size(), "Checking if deck contains the 24 treasure deck cards");
		assertEquals(2,p1.getHand().getDeck().size(), "Checking if player p1 hand contains two cards");
		assertEquals(2,p2.getHand().getDeck().size(), "Checking if player p2 hand contains two cards");
	}

}
