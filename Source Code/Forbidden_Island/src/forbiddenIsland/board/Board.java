package forbiddenIsland.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.enums.TreasureEnums;
/**
 * Board singleton class.
 * A single board object is created and that object is 
 * called using the getInstance method.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class Board {

	//-----------------------------------
    // Variable Setup
	//-----------------------------------
	private static Board        theBoard;
    private IslandTile[][]      boardTiles;
    private char[][]            displayGrid;
    private ArrayList<Position> validTilePositions;
    private int                 numRows;
    private int                 numCols;

    //-----------------------------------
    // Get Instance of Singleton
    //-----------------------------------
    /**
     * getInstance method returns single instance of board.
     * @return theBoard. singleton Board object.
     */
    public static Board getInstance(){
        if(theBoard == null){
            theBoard = new Board();
        }
        return theBoard;
    }

    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Board constructor. 
     * Sets up the board including the island tiles. 
     */
    private Board() {
        // The dimensions of displayGrid needed when printing Board
        this.numRows = 24;
        this.numCols = 47;
        this.validTilePositions = createValidTilePositions();

        // Create blank Display Grid
        this.displayGrid = new char[numCols][numRows];
        initDisplayGrid(); // Initialise Display Grid
        
        // Create blank Tiles for Board
        this.boardTiles = new IslandTile[6][6];
        initBoard(); // Initialise Board
    }

    //----------------------------------------------------------
    // Private methods to help initialise Board and Display Grid
    //----------------------------------------------------------
    /**
     * Creates the ArrayList of Island Tile Positions.
     * Each Island Tile should be defined by the following positions:
     *               {2,5} {3,5}
     *         {1,4} {2,4} {3,4} {4,4}
     *   {0,3} {1,3} {2,3} {3,3} {4,3} {5,3}
     *   {0,2} {1,2} {2,2} {3,2} {4,2} {5,2}
     *         {1,1} {2,1} {3,1} {4,1}
     *               {2,0} {3,0}
     * @return the ArrayList of tile positions on the Board.
     */
    private ArrayList<Position> createValidTilePositions() {
    	// Create an array list of the 24 valid Island Tile positions as seen above
    	ArrayList<Position> validTilePositions = new ArrayList<Position>();
    	// The lowest y values, from the positions above, corresponding to each x from 0 to 5
    	int[] y_low = {2,1,0,0,1,2};

    	// Loop through each x position value
    	for (int x = 0; x < 6; x++) {
    		// Loop through each corresponding y value
    		for (int y = y_low[x]; y < 6 - y_low[x]; y++) {
    			validTilePositions.add(new Position(x,y));
    		}
    	}
    	return validTilePositions;
	}

	/**
     * Initialise the board and set up Island tiles.
     */
    private void initBoard() {
    	// Create an ArrayList of type TilesEnums containing all of the enum values
    	ArrayList<TilesEnums> tilesEnums = new ArrayList<TilesEnums>(EnumSet.allOf(TilesEnums.class));
    	
    	// Island tiles must be shuffled initially
    	Collections.shuffle(validTilePositions);
    	Collections.shuffle(tilesEnums);

    	// Initialise an index
    	int i = 0;
    	// Loop through each valid position and create a new IslandTile at that position
    	for (Position pos: validTilePositions) {
    		boardTiles[pos.getX()][pos.getY()] = new IslandTile(tilesEnums.get(i),pos,StateEnums.DRY);
    		i++;
    	}
	}

    /**
     * Initialise the display grid to be printed.
     * The grid should be filled with spaces initially.
     */
    private void initDisplayGrid() {
    	for (int y=0; y<numRows; y++) {         // For each row in displayGrid
            for (int x=0; x<numCols; x++) {     // For each column in row
                displayGrid[x][y]=' ';
            }
      }
	}

    //-----------------------------------
    // Getters
    //----------------------------------- 
    /**
     * returns the array of Island Tiles.
     * @return the Island tiles on the Board.
     */
    public IslandTile[][] getTiles() {
        return boardTiles;
    }

    /**
     * returns the char array of grid to be displayed.
     * @return the char array.
     */
    public char[][] getDisplayGrid() {
        return displayGrid;
    }

    /**
     * returns the ArrayList of valid Island Tile Positions.
     * @return the ArrayList of Island tile positions.
     */
    public ArrayList<Position> getValidTilePositions() {
        return validTilePositions;
    }

    //----------------------------------------------------------
    // Allow common interactions with tiles via Board
    //----------------------------------------------------------
    /**
     * returns a particular board tile.
     * @param pos the tiles position
     * @return the Island tile for a particular position.
     */
    public IslandTile getTile(Position pos) {
        return boardTiles[pos.getX()][pos.getY()];
    }

    /**
     * returns the Island Tile enum name
     * @param pos the tiles position.
     * @return the name of the Island tile is.
     */
    public TilesEnums getTileType(Position pos) {
        return getTile(pos).getTileName();
    }

    /**
     * returns the IslandTile corresponding to a given TilesEnums.
     * @param tileEnum the the tile name
     * @return the corresponding Island tile on the Board.
     */
    public IslandTile getIslandTile(TilesEnums tileEnum) {
		// Loop through each y position value
    	for (int y = 0; y < 6; y++) {
    		// Loop through each corresponding x value
    		for (int x = 0; x < 6; x++) {
    			// If there exists a valid Island tile and it corresponds to tileEnum return it
    			if(boardTiles[x][y] != null && boardTiles[x][y].getTileName() == tileEnum) { 
    				return boardTiles[x][y];
    			}
    		}
    	}
		return null;
    }

    /**
     * returns the list of diagonal Island tiles
     * @param tile the Island Tile.
     * @return the list of diagonal Island tiles
     */
    public List<IslandTile> getDiagonals(IslandTile tile) {
    	// Create an array list of the diagonal Island tiles
        ArrayList<IslandTile> diagonalTiles = new ArrayList<IslandTile>();
        Position pos = tile.getLoc();

        // deltaPos contains the x and y values to be added to get the diagonal positions
        int[][] deltaPos = {{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int[] delta: deltaPos) {
        	// Create a new diagonal position
            Position diagonalPos = new Position(pos.getX() + delta[0], pos.getY() + delta[1]);
            // Diagonal position must be a valid Island tile position
            if (validTilePositions.contains(diagonalPos)) {
            	IslandTile t = getTile(diagonalPos);
            	// If tile is not sunk, add tile to list
                if (!t.isSunk()) {
                	diagonalTiles.add(t);
                }
            }
        }
        return diagonalTiles;
    }

    /**
     * returns the list of adjacent Island tiles
     * @param tile the Island Tile.
     * @return the list of adjacent Island tiles
     */
    public List<IslandTile> getAdjacent(IslandTile tile) {
    	// Create an array list of the adjacent Island tiles
        ArrayList<IslandTile> adjacentTiles = new ArrayList<IslandTile>();
        Position pos = tile.getLoc();

        // deltaPos contains the x and y values to be added to get the adjacent positions
        int[][] deltaPos = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] delta: deltaPos) {
        	// Create a new adjacent position
            Position adjacentPos = new Position(pos.getX() + delta[0], pos.getY() + delta[1]);
            // Adjacent position must be a valid Island tile position
            if (validTilePositions.contains(adjacentPos)) {
                IslandTile t = getTile(adjacentPos);
                // If tile is not sunk, add tile to list
                if (!t.isSunk()) {
                	adjacentTiles.add(t);
                }
            }
        }
        return adjacentTiles;
    }

    /**
	 * Resets the Treasure characters once corresponding input treasure
	 * has been captured
	 * @param treasure the treasure
	 */
	public void resetTreasures(TreasureEnums treasure) {
		// Loop through each y position value
    	for (int y = 0; y < 6; y++) {
    		// Loop through each corresponding x value
    		for (int x = 0; x < 6; x++) {
    			// If there exists a valid Island tile and it contains the treasure, reset char
    			if(boardTiles[x][y] != null && boardTiles[x][y].getTreasure() == treasure) {
    				int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
    				int[] tLoc = {bL[0] + 1, bL[1] + 1}; // Position of mapChar corresponding to the Treasure
    				displayGrid[tLoc[0]][tLoc[1]] = ' ';
    			}
    		}
    	}
	}

    //-----------------------------------
    // Other
    //-----------------------------------
    /**
     * Void function to print the board with the island tiles, pawns, treasures etc. Public as
     * function simply prints the board which is needed by gameplay package.
     */
    public void printBoard() { // Print the Board to the terminal
        
        String rowString;
        
        for (int y=numRows-1; y>=0; y--) {  // Cycle down through rows in Grid
            rowString = "";

            for (int x=0; x<numCols; x++) { // Cycle across through cols in row
                rowString += displayGrid[x][y];
                rowString += ' ';
            }
            
            System.out.println(rowString); // Print row
        }
    }

    //------------------------------------------
    // Singleton destroyer for unit testing ONLY
    //------------------------------------------
    
    public void destroyMe() {
        theBoard = null;
    }
}