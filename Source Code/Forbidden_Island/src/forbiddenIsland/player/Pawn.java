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
	 * Constructor for a Adventurer forbiddenIsland.adventurer object.
	 * @param islandTile.	 Adventurer Island Tile
	 */
	Pawn(TilesEnums startTileEnum){
		initIslandTile(startTileEnum);
	}

	//-----------------------------------
	// Methods
	//-----------------------------------
	/**
	 * Check if two Pawns are on the same Island Tile.
	 * @param islandTile
	 */
	public boolean equals(Pawn pawn) {
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
	public void initIslandTile(TilesEnums startTileEnum) {
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

}
