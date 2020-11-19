package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.player.Pawn;

/**
 * Class depicting forbiddenIsland.adventurer type Pilot
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Pilot extends Adventurer {
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for a Pilot forbiddenIsland.adventurer role.
     */
	public Pilot() {
		super();
	}
	
	@Override
	/**
	 * Move Adventurer Island Tile.
	 * Can move to any tile. 
	 * @param pawn.	   Player pawn 
	 * @param newTile. New Adventurer Island Tile
	 */
	public void move(Pawn pawn,IslandTile newTile){
			if (!newTile.isSunk())
				pawn.setPawnTile(newTile);
			else
				System.out.println("Error(move): Cannot move to Sunk Island Tile");
	}
}
