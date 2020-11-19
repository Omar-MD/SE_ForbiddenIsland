package forbiddenIsland.adventurer;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.player.Pawn;

/**
 * Class depicting forbiddenIsland.adventurer type Explorer
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Explorer extends Adventurer {
	
	// Get Board Instance
	private Board board;
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for an Engineer forbiddenIsland.adventurer role.
     */
	public Explorer() {
		super();
	}
	
	@Override
	/**
	 * Move Adventurer Island Tile, can only move to neighbouring tiles.
	 * Can move to diagonal tiles. 
	 * @param pawn.	   Player pawn 
	 * @param newTile. New Adventurer Island Tile
	 */
	public void move(Pawn pawn,IslandTile newTile){
		if (this.board.getAdjacent(pawn.getPawnTile()).contains(newTile) ||
				this.board.getDiagonals(pawn.getPawnTile()).contains(newTile)) {
			if (!newTile.isSunk())
				pawn.setPawnTile(newTile);
			else
				System.out.println("Error(move): Cannot move to Sunk Island Tile");
		} 
		else
			System.out.println("Error(move): Cannot move to non-adjacent Island Tile");
	}

}
