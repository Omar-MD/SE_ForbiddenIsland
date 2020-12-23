package forbiddenIsland.player;

import java.util.List;

import forbiddenIsland.adventurer.*;
import forbiddenIsland.board.Board;
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
 * Player class containing the name, adventurer role, pawn and hand in the game of Forbidden Island.
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
	public Player(int playerNum, String name, String role){
		this.name=name;
		setRole(role);
		setPawn(role, playerNum);
		this.hand = new Hand();
		this.team = PlayerList.getInstance();
	}

	//----------------------------
	// Player Actions 
	//----------------------------
	/**
	 * Move Player.  
	 * @param newTile	New Island Tile
	 * @return boolean	True if successful, false otherwise
	 */
	public boolean move(IslandTile newTile){
		return getRole().move(getPawn(),newTile);
	}

	/**
	 * Shore Up Island Tile.
	 * @param shoredTile  ShoredUp IslandTile.
	 * @return boolean	  True if successful, false otherwise
	 */
	public boolean shoreUp(IslandTile shoredTile) {
		return getRole().shoreUp(getPawn().getPawnTile(),shoredTile);
	}

	/**
	 * Swim away from sinking Island Tile.
	 * @param newTile  	new IslandTile.
	 * @return boolean	True if successful, false otherwise
	 */
	public boolean swim(IslandTile newTile) {
		return getRole().swim(getPawn(),newTile);
	}

	/**
	 * Give a treasure card present in player deck to another player.
	 * Except for the Messenger the Treasure card can only be given
	 * if both players are on same Island Tile
	 * @param  card		Treasure Card given.
	 * @param  teamMate	Player receiving card
	 * @return boolean	True if successful, false otherwise
	 */
	public boolean giveTreasureCard(TreasureCard card, Player teamMate) {
		if(hand.getDeck().contains(card)) {
			if(getPawn().isSameTile(teamMate.getPawn()) || (getRole() instanceof Messenger)) {
				hand.getDeck().remove(card);
				teamMate.hand.addCard(card); 
				return true;
			}
			else
				System.out.println("\nError(giveTreasureCard): Pawns must be on the same Island Tile to give Treasure Card");
		}
		else
			System.out.println("\nError(giveTreasureCard): Treasure Card not in player Deck");
		return false;
	}

	/**
	 * Capture Treasure if the Player is on a treasure Tile 
	 * and if the player deck consists of 4 matching treasure cards.
	 * Also discards the used treasure cards to the Treasure Discard pile.
	 * @return boolean	True if successful, false otherwise
	 */
	public boolean captureTreasure() {
		if(getPawn().isTreasureTile()) {
			if(hand.isTreasureDeck()) {
				Board board = Board.getInstance();
				switch(hand.getTreasureName()) {
				case THE_CRYSTAL_OF_FIRE:
					if(getPawn().getPawnTile().getTreasure().equals(TreasureEnums.THE_CRYSTAL_OF_FIRE)){
						team.addCapturedTreasure(new Treasure(TreasureEnums.THE_CRYSTAL_OF_FIRE));
						hand.discardTreasureSet();
						board.resetTreasures(TreasureEnums.THE_CRYSTAL_OF_FIRE);
						return true;
					}else{
						System.out.println("\nError(captureTreasure): Incorrect Treasure Match");
						return false;
					}
				case THE_EARTH_STONE:
					if(getPawn().getPawnTile().getTreasure().equals(TreasureEnums.THE_EARTH_STONE)){
						team.addCapturedTreasure(new Treasure(TreasureEnums.THE_EARTH_STONE));
						hand.discardTreasureSet();
						board.resetTreasures(TreasureEnums.THE_EARTH_STONE);
						return true;
					}else{
						System.out.println("\nError(captureTreasure): Incorrect Treasure Match"); 
						return false;
					}
					case THE_OCEANS_CHALICE:
					if(getPawn().getPawnTile().getTreasure().equals(TreasureEnums.THE_OCEANS_CHALICE)){
						team.addCapturedTreasure(new Treasure(TreasureEnums.THE_OCEANS_CHALICE));
						hand.discardTreasureSet();
						board.resetTreasures(TreasureEnums.THE_OCEANS_CHALICE);
						return true;
					}else{
						System.out.println("\nError(captureTreasure): Incorrect Treasure Match"); 
						return false;
					}
				case THE_STATUE_OF_THE_WIND:
					if(getPawn().getPawnTile().getTreasure().equals(TreasureEnums.THE_STATUE_OF_THE_WIND)){
						team.addCapturedTreasure(new Treasure(TreasureEnums.THE_STATUE_OF_THE_WIND));
						hand.discardTreasureSet();
						board.resetTreasures(TreasureEnums.THE_STATUE_OF_THE_WIND);
						return true;
					}else{
						System.out.println("\nError(captureTreasure): Incorrect Treasure Match"); 
						return false;
					}
				default:
					System.out.println("\nError(captureTreasure): Failed to Capture Treasure"); 
					return false;
				}
			}
			else
				System.out.println("\nError(captureTreasure): Missing Matching Treasure Cards");
		}
		else
			System.out.println("\nError(captureTreasure): Not on Treasure Tile"); 
		return false;
	} 

	//-----------------------------------
	// SpecialCard Actions
	//-----------------------------------
	/**
	 * Use Sandbags Special Card.
	 * @param shoredTile	Shored Island Tile. 
	 * @return boolean		True if successful, false otherwise
	 */
	public boolean useSandbagsCard(IslandTile shoredTile) {
		if(hand.hasSpecialCard(SpecialCardEnums.SANDBAGS)){
			Card c = hand.getCard(hand.getIndex(SpecialCardEnums.SANDBAGS));
			SpecialCard sCard = (SpecialCard) c;
			// If Sandbags card was used successfully, send card to treasure discard pile.
			if (sCard.useSandbags(shoredTile)) {
				TreasureDeck.getInstance().discard(c);
				hand.getDeck().remove(c);
				return true;
			}
		}
		else
			System.out.println("\nError(useSandbagsCard): SandBags card not in Hand"); 
		return false;
	}

	/**
	 * Use HelicopterLift Special Card.
	 * @param flyingPlayers Players to be flown.
	 * @param newTile		Island Tile Destination.
	 * @return boolean		True if successful, false otherwise
	 */
	public boolean useHelicopterLiftCard(List<Player> flyingPlayers,IslandTile newTile) {
		if(hand.hasSpecialCard(SpecialCardEnums.HELICOPTER_LIFT)){
			Card c = hand.getCard(hand.getIndex(SpecialCardEnums.HELICOPTER_LIFT));
			SpecialCard sCard = (SpecialCard) c;
			// If Helicopter Lift card was used successfully, send card to treasure discard pile.
			if (sCard.useHelicopterLift(flyingPlayers, newTile)) {
				TreasureDeck.getInstance().discard(c);
				hand.getDeck().remove(c);
				return true;
			}
		}
		else
			System.out.println("\nError(useHelicopterLiftCard): HelicopterLift card not in Hand"); 
		return false;
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

	/**
	 * Gets the character associated to a Player's Pawn
	 * @return the pawn character
	 */
	public char getChar() {
	    return pawn.getChar();
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
			System.out.println("\nError(setRole): Incorrect Adventurer role String"); 
		}
	}

	/**
	 * Setter for Player pawn.
	 * @param String role	Players role
	 */
	private void setPawn(String role, int playerNum) {
		switch(role) {
		case "Pilot": 
			this.pawn = new Pawn(TilesEnums.FOOLS_LANDING, (char) (playerNum+48)); // Convert int to char (ASCII)
			break; 
		case "Navigator": 
			this.pawn = new Pawn(TilesEnums.GOLD_GATE, (char) (playerNum+48));
			break; 
		case "Messenger": 
			this.pawn = new Pawn(TilesEnums.SILVER_GATE, (char) (playerNum+48));
			break; 
		case "Explorer": 
			this.pawn = new Pawn(TilesEnums.COPPER_GATE, (char) (playerNum+48));
			break; 
		case "Engineer": 
			this.pawn = new Pawn(TilesEnums.BRONZE_GATE, (char) (playerNum+48));
			break; 
		case "Diver": 
			this.pawn = new Pawn(TilesEnums.IRON_GATE, (char) (playerNum+48));
			break; 
		default: 
			System.out.println("Error(setPawn): Incorrect Adventurer role String");
		}
	}

	/**
     * Returns boolean to check if players are the same
     * @return the boolean
     */
    @Override
	public boolean equals(Object o) {
		// Check if o is an instance of Position
		if(o instanceof Player) {
			// Typecast o to Position so that we can compare data members
			Player p = (Player) o;
			// Compare the data members and return accordingly
			return name.equals(p.getName()) && 
					role.toString().equals(p.getRole().toString()) &&
					getChar() == p.getChar();
		}
		return false;
	}

	@Override
	/**
	 * Print Player character and name 
	 * @return String containing necessary Player information
	 */
	public String toString() {
		return "["+ getChar() +"]"+" : " + getName();
	}
}

