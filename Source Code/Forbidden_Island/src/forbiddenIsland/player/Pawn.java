package forbiddenIsland.player;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.enums.TilesEnums;

/**
 * Pawn holding the Players IslandTile
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class Pawn {

	//---------------------------
	// Variable Setup
	//---------------------------
	private IslandTile pawnTile;

	//----------------------------
	// Constructor
	//----------------------------
	/**
	 * Constructor for a player Pawn object.
	 * @param startTileEnum	 Pawn Island Tile
	 */
	Pawn(TilesEnums startTileEnum){
		initPawnTile(startTileEnum);
	}

	//-----------------------------------
	// Methods
	//-----------------------------------
	/**
	 * Check if two Pawns are on the same Island Tile.
	 * @param pawn
	 */
	public boolean isSameTile(Pawn pawn) {
		return getPawnTile().equals(pawn.getPawnTile());
	}
	
	/**
	 * Check for treasure in Island Tile.
	 */
	public boolean isTreasureTile() {
		return getPawnTile().getTreasure() != null;
	}
	
	//-----------------------------------
	// Setters
	//-----------------------------------
	/**
	 * Set Pawn Island Tile
	 * @param islandTile
	 */
	public void setPawnTile(IslandTile islandTile){
		this.pawnTile=islandTile;
	}
	
	/**
	 * Initialize Pawn Island Tile
	 * @param startTileEnum
	 */
	public void initPawnTile(TilesEnums startTileEnum) {
		Board board = Board.getInstance();
		setPawnTile(board.getIslandTile(startTileEnum));
	}
	
	//------------------------------------
	// Getters
	//------------------------------------
	/**
	 * Return Pawn Island Tile
	 * @return pawnTile
	 */
	public IslandTile getPawnTile(){
		return this.pawnTile;
	}

	@Override 
	/**
	 * Print Pawn location 
	 * @return String
	 */
	public String toString() {
		return getPawnTile().getTileName().toString();
	}
}
