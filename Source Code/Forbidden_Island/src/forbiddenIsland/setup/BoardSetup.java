package forbiddenIsland.setup;

import forbiddenIsland.board.*;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * Class to handle all aspects of setting up the Board for a game of Forbidden Island.
 * This class should only exist within the Setup facade.
 * @author  Jithin James and Omar Duadu
 * @version 1.0
 */
public class BoardSetup {

	//-----------------------------------
	// Setup Variables
	//-----------------------------------
	private Board setupBoard;
	private Grid  setupGrid;
	
	//-----------------------------------
	// Constructor
	//-----------------------------------
	/**
	 * Constructor for the BoardSetup Class.
	 */
	public BoardSetup() {
	    // Get first instance of Board
		this.setupBoard = Board.getInstance();
		// Get first instance of Grid
		this.setupGrid = Grid.getInstance();
	}
	
	//-----------------------------------
	// Public methods
	//-----------------------------------
	/**
	 * Build the layout of the Board.
	 */
	public void initialiseBoard() { // Build the layout of the Board
		drawTiles();
		addTreasures();
	}

	/**
	 * Add all the Pawns relating to the Players in PlayerList to the Board.
	 */
	public void addPlayerPawns() { // Add all Players' Pawns to the board
		PlayerList playerList = PlayerList.getInstance();

		for (Player i: playerList.getAllPlayers()) {
			IslandTile pawnTile = i.getPawn().getPawnTile();
			setupGrid.setPawnOnGrid(pawnTile, i.getChar());
		}
	}
	
	//-----------------------------------
    // Private methods
	//-----------------------------------
	/**
	 * Add all the Treasures on the Board.
	 */
	private void addTreasures() {
		IslandTile[][] boardTiles = setupBoard.getTiles();
		// Loop through each y position value
    	for (int y = 0; y < 6; y++) {
    		// Loop through each corresponding x value
    		for (int x = 0; x < 6; x++) {
    			// If there exists a valid Island tile and if the Island tile contains a treasure
    			if(boardTiles[x][y] != null && boardTiles[x][y].getTreasure() != null) {
    				addTreasure(boardTiles[x][y]);
    			}
    		}
    	}	
	}

	/**
	 * Add the Treasure.
	 * @param tile The tile to draw a corresponding treasure
	 */
	private void addTreasure(IslandTile tile) {
		// Get position x and y of Island tile
		int x = tile.getLoc().getX();
		int y = tile.getLoc().getY();
		int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
		int[] tLoc = {bL[0] + 1, bL[1] + 1}; // Position of mapChar corresponding to the Treasure

		// mapChar to represent treasure
		char mapChar = tile.getTreasure().getChar();
		setupGrid.getDisplayGrid()[tLoc[0]][tLoc[1]] = mapChar;
	}

	/**
	 * Draw all the Tiles on the Board.
	 */
	private void drawTiles() {
		IslandTile[][] boardTiles = setupBoard.getTiles();
		// Loop through each y position value
    	for (int y = 0; y < 6; y++) {
    		// Loop through each corresponding x value
    		for (int x = 0; x < 6; x++) {
    			// If there exists a valid Island tile, draw tile
    			if(boardTiles[x][y] != null) {
    				setupGrid.setTileOutline(boardTiles[x][y]);
    			}
    		}
    	}
	}
}
