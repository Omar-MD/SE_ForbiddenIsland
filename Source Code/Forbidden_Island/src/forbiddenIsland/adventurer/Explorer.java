package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.player.Pawn;

/**
 * Class depicting adventurer type Explorer
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Explorer extends Adventurer {
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for an Explorer role.
     */
	public Explorer() {
		super();
	}
	
	//----------------------------
	// Methods
	//----------------------------
	@Override
	/**
	 * Explorer Swim Method, can also swim to diagonal tiles. 
	 * @param pawn	   Player pawn 
	 * @param newTile  New Adventurer Island Tile
	 */
	public boolean swim(Pawn pawn,IslandTile newTile){
		if (this.board.getAdjacent(pawn.getPawnTile()).contains(newTile) ||
				this.board.getDiagonals(pawn.getPawnTile()).contains(newTile)) {
			pawn.setPawnTile(newTile);
			return true;
		} 
		else{
			System.out.println("Error(swim): Cannot swim to chosen Island Tile");
			return false;
		}
	}

	@Override 
	/**
	 * Print Explorer role 
	 * @return String
	 */
	public String toString() {
		return "Explorer";
	}
}
