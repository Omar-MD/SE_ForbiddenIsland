package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.board.Board;

import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.SpecialCard;

import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.enums.SpecialCardEnums;

import forbiddenIsland.player.Pawn;
import forbiddenIsland.player.Player;

import java.util.List;
import java.util.ArrayList;


/**
 * Abstract Class depicting player adventurers
 * in the game of Forbidden Island
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public abstract class Adventurer {
	
	// Get Board Instance
	protected Board board = Board.getInstance();
	
	//-----------------------------------
	// Adventurer Methods
	//-----------------------------------
	/**
	 * Move Adventurer Island Tile, can only move to neighbouring tiles.
	 * Cannot move to diagonal or non-sunk tiles. 
	 * @param pawn.	   Player pawn 
	 * @param newTile. New Adventurer Island Tile
	 */
	public void move(Pawn pawn,IslandTile newTile){
		if (board.getAdjacent(pawn.getPawnTile()).contains(newTile)) {
			if (!newTile.isSunk())
				pawn.setPawnTile(newTile);
			else
				System.out.println("Error(move): Cannot move to Sunk Island Tile");
		} 
		else
			System.out.println("Error(move): Cannot move to non-adjacent Island Tile");
	}

	/**
	 * Shore Up an Island Tile i.e. flip an Island Tile to its Dry state.
	 * Can only shoreUp Adventurer Island Tile, and non-diagonal adjacent tiles. 
	 * @param shoredTile.  Shored up Island Tile.
	 * @param pawnTile.	   Player pawn Island Tile
	 */
	public void shoreUp(IslandTile pawnTile,IslandTile shoredTile) {
		if (pawnTile.equals(shoredTile) || board.getAdjacent(pawnTile).contains(shoredTile)) {
			if(shoredTile.isFlooded())
				pawnTile.setState(StateEnums.DRY);
			
			else if(shoredTile.isDry())
				System.out.println("Error(shoreUp): Cannot Shore Up Dry Island Tile");
		
			else if(shoredTile.isSunk())
				System.out.println("Error(shoreUp): Cannot Shore Up Sunk Island Tile");
		}
		else
			System.out.println("Error(shoreUp): Cannot Shore Up non-adjacent Island Tile");
	}

	//-----------------------------------
	// SpecialCard Methods
	//-----------------------------------

	/**
	 * Use WatersRise Special Card in Adventurer's Hand Deck.  
	 * @param handDeck. 	Player HandDeck
	 * @param card. 		WaterRise card
	 */
	public void useWatersRiseCard(List<Card> handDeck,SpecialCard card) {
		if(handDeck.contains(card))
			if(card.getName().equals(SpecialCardEnums.WATERS_RISE)) {
				card.useWatersRise();
				handDeck.remove(card);
			}
	}

	/**
	 * Use Sandbags Special Card in Adventurer's Hand Deck.
	 * @param handDeck. 	Player HandDeck  
	 * @param card. 	 	Sandbags card.
	 * @param shoredTile.	Shored Island Tile. 
	 */
	public void useSandbagsCard(List<Card> handDeck,SpecialCard card,IslandTile shoredTile) {
		if(handDeck.contains(card))
			if(card.getName().equals(SpecialCardEnums.SANDBAGS)) {
				card.useSandbags(shoredTile);
				handDeck.remove(card);
			}
	}

	/**
	 * Use HelicopterLift Special Card in Adventurer's Hand Deck.  
	 * @param handDeck. 	Player HandDeck
	 * @param card. 		Helicopter card.
	 * @param flyingPlayers.Players to be flown.
	 * @param newTile.		Island Tile Destination.
	 */
	public void useHelicopterLiftCard(List<Card> handDeck,SpecialCard card,List<Player> flyingPlayers,IslandTile newTile) {
		if(handDeck.contains(card))
			if(card.getName().equals(SpecialCardEnums.HELICOPTER_LIFT)) {
				card.useHelicopter(flyingPlayers, newTile);
				handDeck.remove(card);
			}
	}

	// toString Method
	@Override 
	/**
	 * Print Adventurer role 
	 * @return String containing Adventurer role
	 */
	public String toString() {
		return "Adventurer: " + this.getClass();
	}

}

