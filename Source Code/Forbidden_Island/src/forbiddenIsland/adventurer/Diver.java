package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.player.Pawn;

/**
 * Class depicting forbiddenIsland.adventurer type Diver
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Diver extends Adventurer {
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for a Diver forbiddenIsland.adventurer role.
     */
	public Diver() {
		super();
	}
	
	@Override
	/**
	 * Move method for Diver class.
	 * Can move to nearest tile.
	 * @param pawn.	   Player pawn 
	 * @param newTile. New Adventurer Island Tile
	 */
	public void move(Pawn pawn,IslandTile newTile){
//		super.move(pawn, newTile);
	}

}
