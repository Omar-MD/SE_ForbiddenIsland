package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.card.Card;
import forbiddenIsland.card.FloodDeck;

/**
 * JUnit test cases for the FloodDeck class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class FloodDeckTest {

	private FloodDeck floodDeck;

	@BeforeEach
	public void setUp() throws Exception {
		floodDeck = FloodDeck.getInstance();
	}

	@AfterEach
	public void tearDown() throws Exception {
		floodDeck.destroyMe();
	}

	@Test
	public void testDrawCardAndRefill() {
		// Flood Deck is full at the beginning and Discard Pile is empty
		assertEquals(24,floodDeck.getDeck().size(), "Checking if deck contains the 24 flood cards");
		assertEquals(0,floodDeck.getDiscardPile().size(), "Checking if discard pile is empty");

		// Draw one flood card
		Card card = floodDeck.drawCard();
		assertEquals(23,floodDeck.getDeck().size(), "Checking if deck contains one less card after drawing");
		assertEquals(1,floodDeck.getDiscardPile().size(), "Checking if discard pile contains one new card after drawing");
		assertTrue(floodDeck.getDiscardPile().contains(card), "Checking if discard pile contains the drawn card");

		// Refill deck
		floodDeck.refillDeck();
		assertEquals(24,floodDeck.getDeck().size(), "Checking if deck has refilled as expected");
		assertEquals(0,floodDeck.getDiscardPile().size(), "Checking if discard pile is empty");
	}

	@Test
	public void testDrawMultipleCardsAndRefill() {
		// Flood Deck is full at the beginning and Discard Pile is empty
		assertEquals(24,floodDeck.getDeck().size(), "Checking if deck contains the 24 flood cards");
		assertEquals(0,floodDeck.getDiscardPile().size(), "Checking if discard pile is empty");

		// Draw 4 flood cards
		floodDeck.drawCard(20);
		assertEquals(4,floodDeck.getDeck().size(), "Checking if deck contains 20 less cards after drawing");
		assertEquals(20,floodDeck.getDiscardPile().size(), "Checking if discard pile contains 20 new cards after drawing");
		
		// Refill deck
		floodDeck.refillDeck();
		assertEquals(24,floodDeck.getDeck().size(), "Checking if deck has refilled as expected");
		assertEquals(0,floodDeck.getDiscardPile().size(), "Checking if discard pile is empty");
	}

	@Test
	public void testDrawCardWhenDeckIsEmpty() {
		int deckSize = floodDeck.getDeck().size();
		floodDeck.drawCard(deckSize);

		// Check if deck is empty now
		assertEquals(0,floodDeck.getDeck().size(), "Checking if deck is empty after drawing all the cards");
		assertEquals(24,floodDeck.getDiscardPile().size(), "Checking if discard pile contains all flood cards after drawing");
		
		// Draw one flood card
		Card card = floodDeck.drawCard();
		assertEquals(23,floodDeck.getDeck().size(), "Checking if deck has been refilled as expected");
		assertEquals(1,floodDeck.getDiscardPile().size(), "Checking if discard pile contains one new card after drawing");
		assertTrue(floodDeck.getDiscardPile().contains(card), "Checking if discard pile contains the drawn card");
	}

}
