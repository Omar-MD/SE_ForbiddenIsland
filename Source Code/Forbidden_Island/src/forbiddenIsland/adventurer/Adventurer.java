package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.board.Board;
import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.player.Pawn;

/**
 * Abstract Class depicting player adventurers
 * in the game of Forbidden Island
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public abstract class Adventurer {
	
	// Get Board Instance
	protected Board board;
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for an abstract Adventurer role.
     */
	public Adventurer() {
		this.board = Board.getInstance();
	}

	//-----------------------------------
	// Adventurer Methods
	//-----------------------------------
	/**
	 * Move Adventurer, can only move to neighbouring tiles.
	 * Cannot move to diagonal or non-sunk tiles. 
	 * @param pawn	   Player pawn 
	 * @param newTile  New Adventurer Island Tile
	 */
	public void move(Pawn pawn,IslandTile newTile){
		if (this.board.getAdjacent(pawn.getPawnTile()).contains(newTile)) {
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
	 * @param shoredTile   Shored up Island Tile.
	 * @param pawnTile	   Player pawn Island Tile
	 */
	public void shoreUp(IslandTile pawnTile,IslandTile shoredTile) {
		if (pawnTile.equals(shoredTile) || this.board.getAdjacent(pawnTile).contains(shoredTile)) {
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

	/**
	 * Swim method for Adventurer, can only move to neighbouring tiles.
	 * Cannot move to diagonal or non-sunk tiles. 
	 * @param pawn	   Player pawn 
	 * @param newTile  New Adventurer Island Tile
	 */
	public void swim(Pawn pawn,IslandTile newTile){
		move(pawn, newTile);
	}
}
