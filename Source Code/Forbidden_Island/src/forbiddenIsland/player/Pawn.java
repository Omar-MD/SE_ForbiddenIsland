package forbiddenIsland.player;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.Grid;
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
	private char       mapChar;

	//----------------------------
	// Constructor
	//----------------------------
	/**
	 * Constructor for a player Pawn object.
	 * @param startTileEnum	 Pawn Island Tile
	 */
	Pawn(TilesEnums startTileEnum, char mapChar){
		initPawnTile(startTileEnum);
		this.mapChar = mapChar;
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
	 * Method to return the character associated to this
	 * pawn which will be printed to the board.
	 * @return the pawn character
	 */
	public char getChar() {
	    return mapChar;
	}

	/**
	 * Set Pawn Island Tile
	 * @param islandTile
	 */
	public void setPawnTile(IslandTile islandTile){
		// Resets Pawn char on previous tile on Grid
		Grid.getInstance().resetPawnChar(pawnTile, mapChar);
		this.pawnTile=islandTile;
		// Adds Pawn char to new tile on Grid
		Grid.getInstance().setPawnOnGrid(pawnTile, mapChar);
	}
	
	/**
	 * Initialize Pawn Island Tile
	 * @param startTileEnum
	 */
	public void initPawnTile(TilesEnums startTileEnum) {
		Board board = Board.getInstance();
		this.pawnTile=board.getIslandTile(startTileEnum);
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
