package forbiddenIsland.adventurer;

import java.util.List;
import forbiddenIsland.board.IslandTile;

/**
 * Class depicting adventurer type Engineer
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */ 
public class Engineer extends Adventurer {
	
	//----------------------------
	// Constructor
	//----------------------------
	/**
     * Constructor for an Engineer adventurer role.
     */
	public Engineer() {
		super();
	}

	//----------------------------
	// Methods
	//----------------------------
	/**
	 * Shore Up two Island Tiles.
	 * Can only shoreUp Adventurer Island Tile, and non-diagonal adjacent tiles. 
	 * @param shoredTiles  List of IslandTiles to be Shored up
	 * @param pawnTile	   Player pawn Island Tile
	 */
	public void shoreUp(IslandTile pawnTile,List<IslandTile> shoredTiles) {
		if(shoredTiles.size()==1)
			super.shoreUp(pawnTile, shoredTiles.get(0));
		else if(shoredTiles.size()==2){
			super.shoreUp(pawnTile, shoredTiles.get(0));
			super.shoreUp(pawnTile, shoredTiles.get(1));
		}
	}

	@Override 
	/**
	 * Print Engineer role 
	 * @return String
	 */
	public String toString() {
		return "Engineer";
	}
}
