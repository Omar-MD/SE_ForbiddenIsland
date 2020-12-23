package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.adventurer.Pilot;
import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.gameplay.Treasure;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

public class PlayerTest {

	private Board testBoard;
	private PlayerList playerList;
	private TreasureDeck treasureDeck;

	@BeforeEach
	public void setUp() throws Exception {
		testBoard = Board.getInstance();
		playerList = PlayerList.getInstance();
		treasureDeck = TreasureDeck.getInstance();
		testBoard.initBoard(true); // Debug Mode is activated and therefore, tiles are in order of tile enums and not randomised
		// The board will look like this:
		//               {COP} {TOM}
		//         {WAT} {TIP} {TOS} {GOG}
	    //   {BRB} {PHR} {LOL} {HOG} {SIG} {FOL}
	    //   {TWH} {CRF} {MIM} {WHG} {COG} {IRG}
		//         {COA} {OBS} {COS} {BRG}
		//               {DOD} {COE}
	}

	@AfterEach
	public void tearDown() throws Exception {
		testBoard.destroyMe();
		playerList.destroyMe();
		treasureDeck.destroyMe();
	}

	@Test
	public void testMove() {
		// Player is Navigator and therefore pawn tile should be Gold Gate
		Player player = new Player(1, "John", "Navigator");
		assertEquals(testBoard.getIslandTile(TilesEnums.GOLD_GATE), player.getPawn().getPawnTile(), "Pawn start tile must be Gold Gate given player role is set to Navigator");

		// Silver Gate is adjacent to Gold Gate in preset board
		player.move(testBoard.getIslandTile(TilesEnums.SILVER_GATE));
		assertEquals(testBoard.getIslandTile(TilesEnums.SILVER_GATE), player.getPawn().getPawnTile(), "Pawn should have successfully moved to Silver Gate");

		// Whispering Garden is diagonal to Silver Gate in preset board
		player.move(testBoard.getIslandTile(TilesEnums.WHISPERING_GARDEN));
		assertEquals(testBoard.getIslandTile(TilesEnums.SILVER_GATE), player.getPawn().getPawnTile(), "Pawn cannot move to Whispering Garden since it is not an adjacent tile");
	}

	@Test
	public void testShoreUp() {
		// Player is Engineer and therefore pawn tile should be Bronze Gate
		Player player = new Player(1, "John", "Engineer");
		assertEquals(testBoard.getIslandTile(TilesEnums.BRONZE_GATE), player.getPawn().getPawnTile(), "Pawn start tile must be Bronze Gate given player role is set to Engineer");

		IslandTile tileToBeShored_1 = testBoard.getIslandTile(TilesEnums.CAVE_OF_SHADOWS);
		tileToBeShored_1.setState(StateEnums.FLOODED);
		player.shoreUp(tileToBeShored_1);
		assertEquals(StateEnums.DRY, tileToBeShored_1.getState(), "Flooded adjacent tile should be shored up");

		IslandTile tileToBeShored_2 = testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE);
		tileToBeShored_2.setState(StateEnums.FLOODED);
		player.shoreUp(tileToBeShored_2);
		assertEquals(StateEnums.FLOODED, tileToBeShored_2.getState(), "Flooded tile is not adjacent");

		IslandTile tileToBeShored_3 = testBoard.getIslandTile(TilesEnums.CAVE_OF_SHADOWS);
		tileToBeShored_3.setState(StateEnums.SUNK);
		player.shoreUp(tileToBeShored_3);
		assertEquals(StateEnums.SUNK, tileToBeShored_3.getState(), "Sunk tile cannot be shored up");
	}

	@Test
	public void testSwimByDiver() {
		// Player is Diver and therefore pawn tile should be Iron Gate
		Player player = new Player(1, "John", "Diver");
		assertEquals(testBoard.getIslandTile(TilesEnums.IRON_GATE), player.getPawn().getPawnTile(), "Pawn start tile must be Iron Gate given player role is set to Diver");

		// Fools Landing is adjacent to Iron Gate in preset board
		player.swim(testBoard.getIslandTile(TilesEnums.FOOLS_LANDING));
		assertEquals(testBoard.getIslandTile(TilesEnums.FOOLS_LANDING), player.getPawn().getPawnTile(), "Pawn should have successfully swam to Fools Landing");

		// Set State to SUNK for all but one
		for (TilesEnums tileEnum: TilesEnums.values()) {
			if (!tileEnum.equals(TilesEnums.BREAKERS_BRIDGE)) {
				testBoard.getIslandTile(tileEnum).setState(StateEnums.SUNK);
			}
		}
		// Silver Gate was adjacent to Fools Landing in preset board
		player.swim(testBoard.getIslandTile(TilesEnums.SILVER_GATE));
		assertEquals(testBoard.getIslandTile(TilesEnums.FOOLS_LANDING), player.getPawn().getPawnTile(), "Pawn cannot swim to Silver Gate since only one nearest tile remains");

		// Try Breakers Bridge
		player.swim(testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE));
		assertEquals(testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE), player.getPawn().getPawnTile(), "Pawn can only swim to nearest non-sunk tiles");

	}

	@Test
	public void testSwimByExplorer() {
		// Player is Diver and therefore pawn tile should be Copper Gate
		Player player = new Player(1, "John", "Explorer");
		assertEquals(testBoard.getIslandTile(TilesEnums.COPPER_GATE), player.getPawn().getPawnTile(), "Pawn start tile must be Copper Gate given player role is set to Explorer");

		// Silver Gate is adjacent to Copper Gate in preset board
		player.swim(testBoard.getIslandTile(TilesEnums.SILVER_GATE));
		assertEquals(testBoard.getIslandTile(TilesEnums.SILVER_GATE), player.getPawn().getPawnTile(), "Pawn should have successfully swam to Silver Gate");

		// Iron Gate was diagonal to Silver Gate in preset board
		player.swim(testBoard.getIslandTile(TilesEnums.IRON_GATE));
		assertEquals(testBoard.getIslandTile(TilesEnums.IRON_GATE), player.getPawn().getPawnTile(), "Pawn should have successfully swam to Iron Gate");

		// Try Breakers Bridge
		player.swim(testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE));
		assertEquals(testBoard.getIslandTile(TilesEnums.IRON_GATE), player.getPawn().getPawnTile(), "Pawn can only swim to adjacent or diagonal tiles");
	}

	@Test
	public void testFlyByPilot() {
		// Player is Pilot and therefore pawn tile should be Fools Landing
		Player player = new Player(1, "John", "Pilot");
		Pilot pilot = (Pilot) player.getRole();
		assertEquals(testBoard.getIslandTile(TilesEnums.FOOLS_LANDING), player.getPawn().getPawnTile(), "Pawn start tile must be Fools Landing given player role is set to Pilot");

		// Try Breakers Bridge
		pilot.fly(player.getPawn(), testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE));
		assertEquals(testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE), player.getPawn().getPawnTile(), "Pawn can fly to any non-sunk tile since they are a pilot");

		// Try a sunk tile
		testBoard.getIslandTile(TilesEnums.COPPER_GATE).setState(StateEnums.SUNK);
		pilot.fly(player.getPawn(), testBoard.getIslandTile(TilesEnums.COPPER_GATE));
		assertEquals(testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE), player.getPawn().getPawnTile(), "Pawn can only fly to any non-sunk tile");

	}

	@Test
	public void testGiveTreasureCardOnSameTile() {
		// Player 1 is Engineer and therefore pawn tile should be Bronze Gate
		Player player_1 = new Player(1, "John", "Engineer");
		// Player is Explorer and therefore pawn tile should be Copper Gate
		Player player_2 = new Player(2, "James", "Explorer");

		// To bring to the same tile to give treasure card
		player_2.move(testBoard.getIslandTile(TilesEnums.BRONZE_GATE));

		// Give player 1 two treasure cards
		Card card = new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE);
		player_1.getHand().addCard(card);
		player_1.getHand().addCard(card);

		assertEquals(2,player_1.getHand().getDeck().size(), "Player 1 should have 2 treasure cards");
		assertEquals(0,player_2.getHand().getDeck().size(), "Player 2 does not have any cards yet");

		// Give treasure card to player 2
		player_1.giveTreasureCard((TreasureCard)card, player_2);

		assertEquals(1,player_1.getHand().getDeck().size(), "Player 1 should have 1 treasure card after giving");
		assertEquals(1,player_2.getHand().getDeck().size(), "Player 2 should have 1 treasure card after receiving");
	}

	@Test
	public void testGiveTreasureCardNotOnSameTile() {
		// Player 1 is Messenger and therefore pawn tile should be Silver Gate
		Player player_1 = new Player(1, "John", "Messenger");
		// Player is Explorer and therefore pawn tile should be Copper Gate
		Player player_2 = new Player(2, "James", "Explorer");

		// Give player 1 and player 2 different treasure cards
		Card card_1 = new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE);
		Card card_2 = new TreasureCard(TreasureEnums.THE_OCEANS_CHALICE);
		player_1.getHand().addCard(card_1);
		player_2.getHand().addCard(card_2);

		assertEquals(card_1,player_1.getHand().getDeck().get(0), "Player 1 should have 1 Crystal of Fire treasure card");
		assertEquals(card_2,player_2.getHand().getDeck().get(0), "Player 2 should have 1 Oceans Chalice treasure card");

		// Give treasure card to player 1
		player_2.giveTreasureCard((TreasureCard)card_2, player_1);

		assertEquals(1,player_1.getHand().getDeck().size(), "Player 1 should still have 1 treasure card");
		assertEquals(1,player_2.getHand().getDeck().size(), "Player 2 should still have 1 treasure card");

		// Let player 1 who is a Messenger give card
		player_1.giveTreasureCard((TreasureCard)card_1, player_2);
		assertEquals(0,player_1.getHand().getDeck().size(), "Player 1 should have 0 treasure cards after giving");
		assertEquals(2,player_2.getHand().getDeck().size(), "Player 2 should have 2 treasure cards after receiving");
	}

	@Test
	public void testCaptureTreasureOnCorrectTile() {
		// Player 1 is Messenger and therefore pawn tile should be Silver Gate
		Player player_1 = new Player(1, "John", "Messenger");
		playerList.addPlayer(player_1);

		// Give player 3 of the same treasure cards
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_STATUE_OF_THE_WIND));
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_STATUE_OF_THE_WIND));
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_STATUE_OF_THE_WIND));

		// Move player to Howling Garden
		player_1.move(testBoard.getIslandTile(TilesEnums.HOWLING_GARDEN));

		// Try to capture the Statue of the Wind treasure
		player_1.captureTreasure();
		assertEquals(0,playerList.getCapturedTreasure().size(), "Checking the size of captured treasure after invalid capture");
		assertEquals(3,player_1.getHand().getDeck().size(), "Checking the size of player hand after invalid capture");

		// Expected treasure
		Treasure expectedTreasure = new Treasure(TreasureEnums.THE_STATUE_OF_THE_WIND);

		// Give player one more of the same treasure card
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_STATUE_OF_THE_WIND));

		// Try to capture the Statue of the Wind treasure
		player_1.captureTreasure();
		assertEquals(1,playerList.getCapturedTreasure().size(), "Checking the size of captured treasures after capturing");
		assertEquals(0,player_1.getHand().getDeck().size(), "Checking the size of player hand after successful capture");

		// Check the treasure obtained 
		for (Treasure treasure: playerList.getCapturedTreasure()) {
			assertEquals(expectedTreasure, treasure, "Capture The Statue of the Wind");
		}
	}

	@Test
	public void testCaptureTreasureOnInCorrectTile() {
		// Player 1 is Messenger and therefore pawn tile should be Silver Gate
		Player player_1 = new Player(1, "John", "Messenger");
		playerList.addPlayer(player_1);

		// Give player 4 of the same treasure cards
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE));
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE));
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE));
		player_1.getHand().addCard(new TreasureCard(TreasureEnums.THE_CRYSTAL_OF_FIRE));

		// Move player to Howling Garden
		player_1.move(testBoard.getIslandTile(TilesEnums.HOWLING_GARDEN));

		// Try to capture the Statue of the Wind treasure
		player_1.captureTreasure();
		assertEquals(0,playerList.getCapturedTreasure().size(), "Checking the size of captured treasure after invalid capture");
		assertEquals(4,player_1.getHand().getDeck().size(), "Checking the size of player hand after invalid capture");
	}

	@Test
	public void testUseSandbagsCard() {
		// Create Player
		Player player_1 = new Player(1, "John", "Messenger");
		int deckSize = treasureDeck.getDeck().size();
		
		assertEquals(0,player_1.getHand().getDeck().size(), "Hand deck should be empty initially");
		assertEquals(28,treasureDeck.getDeck().size(), "Treasure deck should be full initially");
		assertEquals(0,treasureDeck.getDiscardPile().size(), "Discard pile should be empty initially");

		// Loop for all the cards in treasure deck
		for (int i = 0; i < deckSize; i++) {
			// Draw one card and if card matches Sandbags card, then add to hand else discard
			Card card = treasureDeck.drawCard();
			if (card.getName().equals(SpecialCardEnums.SANDBAGS)) {
				player_1.getHand().addCard(card);
			} else {
				treasureDeck.discard(card);
			}
		}

		assertEquals(2,player_1.getHand().getDeck().size(), "Hand deck should now contain the 2 Sandbags cards");
		assertEquals(0,treasureDeck.getDeck().size(), "Treasure deck should now be empty");
		assertEquals(26,treasureDeck.getDiscardPile().size(), "Discard pile should now contain all the treasure deck minus the cards in hand deck");

		// Set a tile 1 to Flooded
		IslandTile tileToBeShored_1 = testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE);
		tileToBeShored_1.setState(StateEnums.FLOODED);
		player_1.useSandbagsCard(tileToBeShored_1);
		assertEquals(StateEnums.DRY, tileToBeShored_1.getState(), "Flooded tile should be shored up by Sandbags card");

		// Set a tile 2 to Sunk
		IslandTile tileToBeShored_2 = testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW);
		tileToBeShored_2.setState(StateEnums.SUNK);
		player_1.useSandbagsCard(tileToBeShored_2);
		assertEquals(StateEnums.SUNK, tileToBeShored_2.getState(), "Sunk tile cannot be shored up by Sandbags card");
	}

	@Test
	public void testUseHelicopterLiftCard() {
		// Create List of Players
		Player player_1 = new Player(1, "John", "Messenger"); // Start at Silver Gate
		Player player_2 = new Player(2, "James", "Pilot"); // Start at Fools Landing
		Player player_3 = new Player(3, "Steve", "Engineer"); // Start at Bronze Gate
		List<Player> flyingPlayers = new ArrayList<Player>();
		
		int deckSize = treasureDeck.getDeck().size();
		assertEquals(0,player_1.getHand().getDeck().size(), "Hand deck should be empty initially");
		assertEquals(28,treasureDeck.getDeck().size(), "Treasure deck should be full initially");
		assertEquals(0,treasureDeck.getDiscardPile().size(), "Discard pile should be empty initially");

		// Loop for all the cards in treasure deck
		for (int i = 0; i < deckSize; i++) {
			// Draw one card and if card matches Helicopter Lift card, then add to hand else discard
			Card card = treasureDeck.drawCard();
			if (card.getName().equals(SpecialCardEnums.HELICOPTER_LIFT)) {
				player_1.getHand().addCard(card);
			} else {
				treasureDeck.discard(card);
			}
		}

		assertEquals(3,player_1.getHand().getDeck().size(), "Hand deck should now contain the 3 Helicopter Lift cards");
		assertEquals(0,treasureDeck.getDeck().size(), "Treasure deck should now be empty");
		assertEquals(25,treasureDeck.getDiscardPile().size(), "Discard pile should now contain all the treasure deck minus the cards in hand deck");

		// Try to fly one player to another tile
		flyingPlayers.add(player_2);
		player_1.useHelicopterLiftCard(flyingPlayers, testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE));
		assertEquals(testBoard.getIslandTile(TilesEnums.BREAKERS_BRIDGE), player_2.getPawn().getPawnTile(), "Pawn should have successfully flown to Breakers Bridge");

		// Try to fly all the players to another tile
		flyingPlayers.add(player_1);
		flyingPlayers.add(player_3);
		player_1.useHelicopterLiftCard(flyingPlayers, testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW));
		assertEquals(testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW), player_1.getPawn().getPawnTile(), "Pawn should have successfully flown to Twilight Hollow");
		assertEquals(testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW), player_2.getPawn().getPawnTile(), "Pawn should have successfully flown to Twilight Hollow");
		assertEquals(testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW), player_3.getPawn().getPawnTile(), "Pawn should have successfully flown to Twilight Hollow");

		// Set a tile to Sunk
		IslandTile sunkTile = testBoard.getIslandTile(TilesEnums.DUNES_OF_DECEPTION);
		sunkTile.setState(StateEnums.SUNK);
		player_1.useHelicopterLiftCard(flyingPlayers, testBoard.getIslandTile(TilesEnums.DUNES_OF_DECEPTION));
		assertEquals(testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW), player_1.getPawn().getPawnTile(), "Pawn should have not flown to Dunes of Deception since it is sunk");
		assertEquals(testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW), player_2.getPawn().getPawnTile(), "Pawn should have not flown to Dunes of Deception since it is sunk");
		assertEquals(testBoard.getIslandTile(TilesEnums.TWILIGHT_HOLLOW), player_3.getPawn().getPawnTile(), "Pawn should have not flown to Dunes of Deception since it is sunk");

	}

}
