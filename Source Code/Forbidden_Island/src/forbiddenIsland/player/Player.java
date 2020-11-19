package forbiddenIsland.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import forbiddenIsland.adventurer.*;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.Card;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.gameplay.Treasure;

public class Player {

	//---------------------------
	// Variable Setup
	//---------------------------
	private String name;
	private Adventurer role;
	private Pawn pawn;
	private List<Card> handDeck;
	private HashSet<Treasure> capturedTreasure;

	//----------------------------
	// Constructor
	//----------------------------
	/**
	 * Constructor for a Player object.
	 * @param name.	 Player Name
	 * @param role.	 String depicting Adventurer Role
	 * 
	 */
	public Player(String name, String role){
		this.name=name;
		setRole(role);
		setPawn(role);
		this.handDeck = new ArrayList<Card>();
		this.capturedTreasure = new HashSet<Treasure>();
	}

	//----------------------------
	// Player Actions 
	//----------------------------

	/**
	 * Move Player.  
	 * @param newTile. New Island Tile
	 */
	public void move(IslandTile newTile){
		getRole().move(getPawn(),newTile);
	}

	/**
	 * Shore Up Island Tile.
	 * @param shoredTile. 	 ShoredUp IslandTile.
	 */
	public void shoreUp(IslandTile shoredTile) {
		getRole().shoreUp(getPawn().getPawnTile(),shoredTile);
	}

	/**
	 * Give a treasure card present in player deck to another player.
	 * Except for the Messenger forbiddenIsland.adventurer.Treasure card, can only be given
	 * if both players are on same Island Tile
	 * @param card.  	 Treasure Card given.
	 * @param teamMate.	 Player receiving card
	 */
	public void giveTreasurerCard(TreasureCard card, Player teamMate) {
		if(getHandDeck().contains(card)) {
			if(getPawn().equals(teamMate.getPawn()) || (getRole() instanceof Messenger)) {
				getHandDeck().remove(card);
				teamMate.addCard(card); 
			}
			else
				System.out.println("Error(giveTreasureCard): Pawns must be on the same Island Tile to give Treasure Card");
		}
		else
			System.out.println("Error(giveTreasureCard): Treasure Card not in player Deck");
	}


	/**
	 * Capture Treasure if the Player is on a treasure Tile 
	 * and if the player deck consists of the full matching trasurer cards.
	 */
	public void captureTreasure() {
		if(getPawn().isTreasureTile()) {
			if(isTreasureDeck()) {
				switch(getTreasureName()) {
				case THE_CRYSTAL_OF_FIRE:
					if(getTreasureName().equals(TreasureEnums.THE_CRYSTAL_OF_FIRE)) {
						addCapturedTreasure(new Treasure(TreasureEnums.THE_CRYSTAL_OF_FIRE));
						getHandDeck().clear();
						break;
					}
				case THE_EARTH_STONE:
					if(getTreasureName().equals(TreasureEnums.THE_EARTH_STONE)) {
						addCapturedTreasure(new Treasure(TreasureEnums.THE_EARTH_STONE));
						getHandDeck().clear();
						break;
					}
				case THE_OCEANS_CHALICE:
					if(getTreasureName().equals(TreasureEnums.THE_OCEANS_CHALICE)) {
						addCapturedTreasure(new Treasure(TreasureEnums.THE_OCEANS_CHALICE));
						getHandDeck().clear();
						break;
					}
				case THE_STATUE_OF_THE_WIND:
					if(getTreasureName().equals(TreasureEnums.THE_STATUE_OF_THE_WIND)) {
						addCapturedTreasure(new Treasure(TreasureEnums.THE_STATUE_OF_THE_WIND));
						getHandDeck().clear();
						break;
					}
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

	/**
	 * Verify Deck consists of a full matching trasurer card.
	 * @param list. 	HandDeck
	 */
	private boolean isTreasureDeck() {
		if(getHandDeck().size() == 5)
			return (new HashSet<Card>(getHandDeck()).size() == 1);
		return false;
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
	 * Use WatersRise Special Card.  
	 * @param card. 		WaterRise card
	 */
	public void watersRiseCard(SpecialCard card) {
		getRole().useWatersRiseCard(getHandDeck(), card);
	}

	/**
	 * Use Sandbags Special Card.  
	 * @param card. 	 	Sandbags card.
	 * @param shoredTile.	Shored Island Tile. 
	 */
	public void useSandbagsCard(SpecialCard card,IslandTile shoredTile) {
		getRole().useSandbagsCard(getHandDeck(), card, shoredTile);
	}

	/**
	 * Use HelicopterLift Special Card.
	 * @param card. 		Helicopter card.
	 * @param flyingPlayers.Players to be flown.
	 * @param newTile.		Island Tile Destination.
	 */
	public void useHelicopterLiftCard(SpecialCard card, List<Player> flyingPlayers, IslandTile newTile) {
		getRole().useHelicopterLiftCard(getHandDeck(), card, flyingPlayers, newTile);
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
				"\nHand Deck: " + getHandDeck().toString()+
				"\nCaptured Treasure: " + getCapturedTreasure().toString();
	}

	//----------------------------
	// Getters 
	//----------------------------
	/**
	 * Return Player name.
	 * @return name. Player Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return Player role.
	 * @return role. Adventurer role
	 */
	public Adventurer getRole() {
		return this.role;
	}

	/**
	 * Return Player pawn 
	 * @return pawn.
	 */
	public Pawn getPawn(){
		return this.pawn;
	}

	/**
	 * Return Player Hand Deck 
	 * @return handDeck.
	 */
	public List<Card> getHandDeck(){
		return this.handDeck;
	}

	/**
	 * Return set of Player captured treasure.
	 * @return capturedTreasure. Player captured treasure set
	 */
	public HashSet<Treasure> getCapturedTreasure() {
		return this.capturedTreasure;
	}

	/**
	 * Return Name of Treasure to be captured.
	 * @return Treasure name.
	 */
	public TreasureEnums getTreasureName() {
		return  (TreasureEnums) this.handDeck.get(0).getName();
	}

	//----------------------------
	// Setters 
	//----------------------------

	/**
	 * Setter for Player forbiddenIsland.adventurer role.
	 * @param String role.	  Players role
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
	 * @param String role.	  Players role
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

	/**
	 * Adds new Player captured treasure.
	 * @param capturedTreasure.	 New captured treasure
	 */
	public void addCapturedTreasure(Treasure capturedTreasure) {
		this.capturedTreasure.add(capturedTreasure);
	}

	/**
	 * Adds new Player card to hand deck.
	 * @param card.	
	 */
	public void addCard(Card card) {
		this.handDeck.add(card);
	}
}

