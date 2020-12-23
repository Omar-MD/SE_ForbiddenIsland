package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.player.Pawn;

/**
 * Class depicting adventurer type Pilot in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Pilot extends Adventurer {
	
	//-----------------------------------
	// Constructor
	//-----------------------------------
	/**
     * Constructor for a Pilot adventurer role.
     */
	public Pilot() {
		super();
	}
	
	//-----------------------------------
	// Methods
	//-----------------------------------
	/**
	 * Pilot fly method. Can move to any tile.
	 * @param pawn	   Player pawn 
	 * @param newTile  New Adventurer Island Tile
	 * @return boolean True if successful, false otherwise
	 */
	public boolean fly(Pawn pawn,IslandTile newTile){
		if (!newTile.isSunk()){
			pawn.setPawnTile(newTile);
			return true;
		}
		else{
			System.out.println("Error(fly): Cannot fly to Sunk Island Tile");
			return false;
		}
	}

	@Override 
	/**
	 * Print Pilot role.
	 * @return String
	 */
	public String toString() {
		return "Pilot";
	}
}
