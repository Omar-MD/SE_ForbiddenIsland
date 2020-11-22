package forbiddenIsland.player;

import java.util.List;
import forbiddenIsland.adventurer.*;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.gameplay.Treasure;

/**
 * Player class containing the name,adventurer role, pawn and hand in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class Player {

	//---------------------------
	// Variable Setup
	//---------------------------
	private String name;
	private Adventurer role;
	private Pawn pawn;
	private Hand hand;
	private PlayerList team;

	//----------------------------
	// Constructor
	//----------------------------
	/**
	 * Constructor for a Player object.
	 * @param name	 Player Name
	 * @param role	 String depicting Adventurer Role
	 * 
	 */
	public Player(String name, String role){
		this.name=name;
		setRole(role);
		setPawn(role);
		this.hand = new Hand();
		this.team = PlayerList.getInstance();
	}

	//----------------------------
	// Player Actions 
	//----------------------------
	/**
	 * Move Player.  
	 * @param newTile		New Island Tile
	 */
	public void move(IslandTile newTile){
		getRole().move(getPawn(),newTile);
	}

	/**
	 * Shore Up Island Tile.
	 * @param shoredTile	ShoredUp IslandTile.
	 */
	public void shoreUp(IslandTile shoredTile) {
		getRole().shoreUp(getPawn().getPawnTile(),shoredTile);
	}

	/**
	 * Give a treasure card present in player deck to another player.
	 * Except for the Messenger the Treasure card can only be given
	 * if both players are on same Island Tile
	 * @param card		Treasure Card given.
	 * @param teamMate	Player receiving card
	 */
	public void giveTreasurerCard(TreasureCard card, Player teamMate) {
		if(hand.getDeck().contains(card)) {
			if(getPawn().isSameTile(teamMate.getPawn()) || (getRole() instanceof Messenger)) {
				hand.getDeck().remove(card);
				teamMate.hand.addCard(card); 
			}
			else
				System.out.println("Error(giveTreasureCard): Pawns must be on the same Island Tile to give Treasure Card");
		}
		else
			System.out.println("Error(giveTreasureCard): Treasure Card not in player Deck");
	}

	/**
	 * Capture Treasure if the Player is on a treasure Tile 
	 * and if the player deck consists of 4 matching treasure cards.
	 * Also discards the used treasure cards to the Treasure Discard pile.
	 */
	public void captureTreasure() {
		if(getPawn().isTreasureTile()) {
			if(hand.isTreasureDeck()) {
				switch(hand.getTreasureName()) {
				case THE_CRYSTAL_OF_FIRE:
					team.addCapturedTreasure(new Treasure(TreasureEnums.THE_CRYSTAL_OF_FIRE));
					hand.discardTreasureSet();
					break;
				case THE_EARTH_STONE:
					team.addCapturedTreasure(new Treasure(TreasureEnums.THE_EARTH_STONE));
					hand.discardTreasureSet();
					break;
				case THE_OCEANS_CHALICE:
					team.addCapturedTreasure(new Treasure(TreasureEnums.THE_OCEANS_CHALICE));
					hand.discardTreasureSet();
					break;
				case THE_STATUE_OF_THE_WIND:
					team.addCapturedTreasure(new Treasure(TreasureEnums.THE_STATUE_OF_THE_WIND));
					hand.discardTreasureSet();
					break;
				default:
					System.out.println("Error(captureTreasure): Failed to Capture Treasure"); 
				}
			}
			else
				System.out.println("Error(captureTreasure): Missing Matching Treasure Cards"); 
		}
		else
			System.out.println("Error(captureTreasure): Not on Treasure Tile"); 
	} 

	//	public void toVictory() {
	//	// Check if all Forbidden Island Players are present, and on Fool's Landing 
	//	if(flyingPlayers.containsAll(team.getTeamList()) && 
	//			StartPosition.getTileName().equals(TilesEnums.FOOLS_LANDING)) {
	//
	//		System.out.println("Msg: Arrived at Finish");
	//		////////////////////////
	//		// Logic for Game Finish
	//		////////////////////////
	//	}else {
	//		System.out.println("Msg: Successfull Transport to: "+destination.getTileName().toString());
	//	}
	//}

	//-----------------------------------
	// SpecialCard Actions
	//-----------------------------------
	/**
	 * Use Sandbags Special Card.
	 * @param shoredTile	Shored Island Tile. 
	 */
	public void useSandbagsCard(IslandTile shoredTile) {
		if(hand.hasSpecialCard(SpecialCardEnums.SANDBAGS)){
			Card c = hand.getCard(hand.getIndex(SpecialCardEnums.SANDBAGS));
			SpecialCard sCard = (SpecialCard) c;
			// If Sandbags card was used successfully, send card to treasure discard pile.
			if (sCard.useSandbags(shoredTile)) {
				TreasureDeck.getInstance().discard(c);
				hand.getDeck().remove(c);
			}
		}
		else
			System.out.println("Error(useSandbagsCard): SandBags card not in Hand"); 
	}

	/**
	 * Use HelicopterLift Special Card.
	 * @param flyingPlayers Players to be flown.
	 * @param newTile		Island Tile Destination.
	 */
	public void useHelicopterLiftCard(List<Player> flyingPlayers,IslandTile newTile) {
		if(hand.hasSpecialCard(SpecialCardEnums.HELICOPTER_LIFT)){
			Card c = hand.getCard(hand.getIndex(SpecialCardEnums.HELICOPTER_LIFT));
			SpecialCard sCard = (SpecialCard) c;
			// If Helicopter Lift card was used successfully, send card to treasure discard pile.
			if (sCard.useHelicopterLift(flyingPlayers, newTile)) {
				TreasureDeck.getInstance().discard(c);
				hand.getDeck().remove(c);
			}
		}
		else
			System.out.println("Error(useHelicopterLiftCard): HelicopterLift card not in Hand"); 
	}

	//----------------------------
	// Getters 
	//----------------------------
	/**
	 * Return Player name.
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return Player role.
	 * @return role
	 */
	public Adventurer getRole() {
		return this.role;
	}

	/**
	 * Return Player pawn
	 * @return pawn
	 */
	public Pawn getPawn(){
		return this.pawn;
	}

	/**
	 * Return Player hand
	 * @return hand
	 */
	public Hand getHand(){
		return this.hand;
	}

	//----------------------------
	// Setters 
	//----------------------------
	/**
	 * Setter for Player adventurer role.
	 * @param String role	Players role
	 */
	private void setRole(String role) {
		switch(role) {
		case "Pilot": 
			this.role = new Pilot(); 
			break; 
		case "Navigator": 
			this.role = new Navigator(); 
			break; 
		case "Messenger": 
			this.role = new Messenger(); 
			break; 
		case "Explorer": 
			this.role = new Explorer(); 
			break; 
		case "Engineer": 
			this.role = new Engineer(); 
			break; 
		case "Diver": 
			this.role = new Diver(); 
			break; 
		default: 
			System.out.println("Error(setRole): Incorrect Adventurer role String"); 
		}
	}

	/**
	 * Setter for Player pawn.
	 * @param String role	Players role
	 */
	private void setPawn(String role) {
		switch(role) {
		case "Pilot": 
			this.pawn = new Pawn(TilesEnums.FOOLS_LANDING); 
			break; 
		case "Navigator": 
			this.pawn = new Pawn(TilesEnums.GOLD_GATE); 
			break; 
		case "Messenger": 
			this.pawn = new Pawn(TilesEnums.SILVER_GATE); 
			break; 
		case "Explorer": 
			this.pawn = new Pawn(TilesEnums.COPPER_GATE); 
			break; 
		case "Engineer": 
			this.pawn = new Pawn(TilesEnums.BRONZE_GATE); 
			break; 
		case "Diver": 
			this.pawn = new Pawn(TilesEnums.IRON_GATE); 
			break; 
		default: 
			System.out.println("Error(setPawn): Incorrect Adventurer role String"); 
		}
	}

	@Override
	/**
	 * Print Player state 
	 * @return String containing Player state
	 */
	public String toString() {
		return "Name: " + getName() +
				"\nAdventurer: " + getRole().toString() +
				"\nLocation: " + getPawn().toString() +
				"\nHand Deck: " + hand.getDeck().toString()+
				"\nCaptured Treasure: " + team.getCapturedTreasure().toString();
	}
}

