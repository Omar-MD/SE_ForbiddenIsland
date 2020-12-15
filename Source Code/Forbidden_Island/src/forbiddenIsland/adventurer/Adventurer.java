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
	 * @param  pawn	   	Player pawn 
	 * @param  newTile  New Adventurer Island Tile
	 * @return boolean	True if successful, false otherwise
	 */
	public boolean move(Pawn pawn,IslandTile newTile){
		if (this.board.getAdjacent(pawn.getPawnTile()).contains(newTile)) {
			pawn.setPawnTile(newTile);
			return true;
		} 
		else
			System.out.println("Error(move): Cannot move to non-adjacent Island Tile.");
		return false;
	}

	/**
	 * Shore Up an Island Tile i.e. flip an Island Tile to its Dry state.
	 * Can only shoreUp Adventurer Island Tile, and non-diagonal adjacent tiles. 
	 * @param  shoredTile   Shored up Island Tile.
	 * @param  pawnTile	    Player pawn Island Tile
	 * @return boolean		True if successful, false otherwise
	 */
	public boolean shoreUp(IslandTile pawnTile,IslandTile shoredTile) {
		if (pawnTile.equals(shoredTile) || this.board.getAdjacent(pawnTile).contains(shoredTile)) {
			if(shoredTile.isFlooded()){
				shoredTile.setState(StateEnums.DRY);
				return true;
			}
			else if(shoredTile.isDry()){
				System.out.println("Error(shoreUp): Cannot Shore Up Dry Island Tile.");
				return false;
			}
			else if(shoredTile.isSunk()){
				System.out.println("Error(shoreUp): Cannot Shore Up Sunk Island Tile.");
				return false;
			}
		}
		else
			System.out.println("Error(shoreUp): Cannot Shore Up non-adjacent Island Tile");
		return false;
	}

	/**
	 * Swim method for Adventurer, can only move to neighbouring tiles.
	 * Cannot move to diagonal or non-sunk tiles. 
	 * @param pawn	   Player pawn 
	 * @param newTile  New Adventurer Island Tile
	 */
	public boolean swim(Pawn pawn,IslandTile newTile){
		return move(pawn, newTile);
	}
}

