package forbiddenIsland.adventurer;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.player.Pawn;

/**
 * Class depicting adventurer type Diver in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class Diver extends Adventurer {

	// ----------------------------
	// Constructor
	// ----------------------------
	/**
	 * Constructor for a Diver adventurer role.
	 */
	public Diver() {
		super();
	}

	// ----------------------------
	// Methods
	// ----------------------------
	@Override
	/**
	 * Swim method for Diver class. Can move to nearest tile.
	 * 
	 * @param pawn    Player pawn
	 * @param newTile New Adventurer Island Tile
	 */
	public void swim(Pawn pawn, IslandTile newTile) {
		if (this.board.getNearestTiles(pawn.getPawnTile()).contains(newTile)) {
			pawn.setPawnTile(newTile);
		}
		else
			System.out.println("Error(swim): Cannot swim to chosen Island Tile");
	}

	@Override
	/**
	 * Print Diver role
	 * @return String
	 */
	public String toString() {
		return "Diver";
	}
}
