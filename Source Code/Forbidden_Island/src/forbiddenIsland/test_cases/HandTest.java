package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.card.Card;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.player.Hand;

/**
 * JUnit test cases for the Hand class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class HandTest {

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
	public void testDiscardTreasureSet() {
		// Initialise a hand deck
		Hand hand = new Hand();
		int deckSize = treasureDeck.getDeck().size();
		
		assertEquals(0,hand.getDeck().size(), "Hand deck should be empty initially");
		assertEquals(28,treasureDeck.getDeck().size(), "Treasure deck should be full initially");
		assertEquals(0,treasureDeck.getDiscardPile().size(), "Discard pile should be empty initially");

		// Loop for all the cards in treasure deck
		for (int i = 0; i < deckSize; i++) {
			// Draw one card and if card matches 'The Crystal of Fire' card, then add to hand else discard
			Card card = treasureDeck.drawCard();
			if (card.getName().equals(TreasureEnums.THE_CRYSTAL_OF_FIRE)) {
				hand.addCard(card);
			} else {
				treasureDeck.discard(card);
			}
		}

		assertEquals(5,hand.getDeck().size(), "Hand deck should now contain the 5 'Crystal of Fire' treasure cards");
		assertEquals(0,treasureDeck.getDeck().size(), "Treasure deck should now be empty");
		assertEquals(23,treasureDeck.getDiscardPile().size(), "Discard pile should now contain all the treasure deck minus the cards in hand deck");

		// Discard treasure card set
		hand.discardTreasureSet();
		assertEquals(1,hand.getDeck().size(), "Hand deck should now contain 1 'Crystal of Fire' treasure card after discarding set");
		assertEquals(0,treasureDeck.getDeck().size(), "Treasure deck should still be empty");
		assertEquals(27,treasureDeck.getDiscardPile().size(), "Discard pile should contain all the treasure deck minus the cards in hand deck");

	}

	@Test
	public void testHasSpecialCard() {
		// Initialise a hand deck
		Hand hand = new Hand();
		hand.addCard(new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE));
		hand.addCard(new SpecialCard(SpecialCardEnums.HELICOPTER_LIFT));
		hand.addCard(new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE));

		// Check if hand contains helicopter lift and sandbags cards
		assertTrue(hand.hasSpecialCard(SpecialCardEnums.HELICOPTER_LIFT), "Checking if hand contains helicopter lift card");
		assertFalse(hand.hasSpecialCard(SpecialCardEnums.SANDBAGS), "Checking if hand contains no sandbags cards");

		// Add sandbags card to hand and check
		hand.addCard(new SpecialCard(SpecialCardEnums.SANDBAGS));
		assertTrue(hand.hasSpecialCard(SpecialCardEnums.SANDBAGS), "Checking if hand contains a sandbags card now");

	}

	@Test
	public void testGetTreasureCardSet_1() {
		// Initialise a hand deck
		Hand hand = new Hand();
		Card cardInHand = new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE);
		hand.addCard(cardInHand);
		hand.addCard(cardInHand);
		hand.addCard(cardInHand);
		hand.addCard(cardInHand);
		hand.addCard(cardInHand);

		List<TreasureCard> tCardSet = hand.getTreasureCardSet();
		assertEquals(4,tCardSet.size(), "Checking the size of the treasure card set needed for capturing a treasure");
		for (TreasureCard tCard: tCardSet) {
			assertEquals(cardInHand,tCard, "Checking the treasure card is the same as expected");
		}
	}

	@Test
	public void testGetTreasureCardSet_2() {
		// Initialise a hand deck
		Hand hand = new Hand();
		Card cardInHand = new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE);

		hand.addCard(cardInHand);
		hand.addCard(new TreasureCard(TreasureEnums.THE_EARTH_STONE));
		hand.addCard(cardInHand);
		hand.addCard(new SpecialCard(SpecialCardEnums.HELICOPTER_LIFT));
		hand.addCard(cardInHand);

		// Try get Treasure Card Set 1
		List<TreasureCard> tCardSet_1 = hand.getTreasureCardSet();
		assertNotEquals(4,tCardSet_1.size(), "4 Treasure Cards are needed for capturing a treasure");

		// Add last treasure card needed to complete a treasure set
		hand.addCard(cardInHand);
		// Try get Treasure Card set 2
		List<TreasureCard> tCardSet_2 = hand.getTreasureCardSet();
		assertEquals(4,tCardSet_2.size(), "Checking the size of the treasure card set needed for capturing a treasure");
		for (TreasureCard tCard: tCardSet_2) {
			assertEquals(cardInHand,tCard, "Checking the treasure card is the same as expected");
		}
	}

}
