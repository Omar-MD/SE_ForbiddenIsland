package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.card.Card;
import forbiddenIsland.card.TreasureDeck;

/**
 * JUnit test cases for the TreasureDeck class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class TreasureDeckTest {

	private TreasureDeck treasureDeck;

	@BeforeEach
	public void setUp() throws Exception {
		treasureDeck = TreasureDeck.getInstance();
	}

	@AfterEach
	public void tearDown() throws Exception {
		treasureDeck.destroyMe();
	}

	@Test
	public void testDrawCardAndRefill() {
		// Treasure Deck is full at the beginning and Discard Pile is empty
		assertEquals(28,treasureDeck.getDeck().size(), "Checking if deck contains the 28 treasure deck cards");
		assertEquals(0,treasureDeck.getDiscardPile().size(), "Checking if discard pile is empty");

		// Draw one card and discard the treasure card obtained
		Card card = treasureDeck.drawCard();
		treasureDeck.discard(card);
		assertEquals(27,treasureDeck.getDeck().size(), "Checking if deck contains one less card after drawing");
		assertEquals(1,treasureDeck.getDiscardPile().size(), "Checking if discard pile contains one new card after discarding the drawn card");
		assertTrue(treasureDeck.getDiscardPile().contains(card), "Checking if discard pile contains the drawn card");

		// Refill deck
		treasureDeck.refillDeck();
		assertEquals(28,treasureDeck.getDeck().size(), "Checking if deck has refilled as expected");
		assertEquals(0,treasureDeck.getDiscardPile().size(), "Checking if discard pile contains one new card after drawing");
	}

	@Test
	public void testDrawCardWhenDeckIsEmpty() {
		// Draw all the cards in the deck and discard them
		int deckSize = treasureDeck.getDeck().size();
		for (int i = 0; i < deckSize; i++) {
			Card c = treasureDeck.drawCard();
			treasureDeck.discard(c);
		}

		// Check if deck is empty now
		assertEquals(0,treasureDeck.getDeck().size(), "Checking if deck is empty after drawing all the cards");
		assertEquals(28,treasureDeck.getDiscardPile().size(), "Checking if discard pile contains all the treasure cards after discarding");
		
		// Draw one card and discard the treasure card obtained
		Card card = treasureDeck.drawCard();
		treasureDeck.discard(card);
		assertEquals(27,treasureDeck.getDeck().size(), "Checking if deck has been refilled as expected");
		assertEquals(1,treasureDeck.getDiscardPile().size(), "Checking if discard pile contains one new card after drawing");
		assertTrue(treasureDeck.getDiscardPile().contains(card), "Checking if discard pile contains the drawn card");
	}
}
