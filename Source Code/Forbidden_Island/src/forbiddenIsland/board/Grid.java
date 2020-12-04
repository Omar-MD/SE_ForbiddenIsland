package forbiddenIsland.board;

/**
 * Grid singleton class.
 * A single grid object is created and that object is 
 * called using the getInstance method.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class Grid {

	//-----------------------------------
    // Variable Setup
	//-----------------------------------
	private static Grid theGrid;
    private char[][]    displayGrid;
    private int         numRows;
    private int         numCols;

    //-----------------------------------
    // Get Instance of Singleton
    //-----------------------------------
    /**
     * getInstance method returns single instance of grid.
     * @return theGrid. singleton Grid object.
     */
    public static Grid getInstance(){
        if(theGrid == null){
        	theGrid = new Grid();
        }
        return theGrid;
    }

    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Grid constructor. 
     * Sets up the display grid. 
     */
    private Grid() {
        // The dimensions of displayGrid needed when printing Board
        this.numRows = 24;
        this.numCols = 47;

        // Create blank Display Grid
        this.displayGrid = new char[numCols][numRows];
        initDisplayGrid(); // Initialise Display Grid
    }

    //----------------------------------------------------------
    // Private method to help initialise Display Grid
    //----------------------------------------------------------
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

    //----------------------------------------------------------
    // Methods to draw/update Island tiles on the Grid
    //----------------------------------------------------------
    /**
	 * Outline the tile to represent in the form of a grid.
	 */
	public void setTileOutline(IslandTile tile) {
		// Get position x and y of Island tile
		int x = tile.getLoc().getX();
		int y = tile.getLoc().getY();

		// Translating tile position to display grid location
		int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
		int[] bR = {bL[0] + 6, bL[1]}; // Bottom right corner of a tile
		int[] tL = {bL[0], bL[1] + 3}; // Top left corner of a tile
		int[] tR = {bL[0] + 6, bL[1] + 3}; // Top right corner of a tile		

		// mapChar to represent tile state i.e. dry, flooded or sunk
		char mapChar = tile.getState().getChar();
		drawSide(mapChar, bL[0],bL[1] + 1,tL[1]);   // Left side of tile
		drawSide(mapChar, bR[0],bR[1] + 1,tR[1]);   // Right side of tile
		drawBottom(mapChar, bL[1],bL[0],bR[0]); // Bottom of tile
		drawTop(tile, tL[1], tL[0] + 2); // Top of tile
	}

	/**
	 * Draw the top of Island Tile
	 * @param tile The Island Tile
	 * @param y The row to draw the tile
	 * @param xStart The beginning of the top of Tile
	 */
	private void drawTop(IslandTile tile, int y, int xStart) {
		// If tile is sunk, replace top of tile with space chars
		if (tile.isSunk()) {
			for (int x = xStart; x < xStart + 3; x++) {
				displayGrid[x][y] = ' ';
			}
		}
		// If tile is not sunk, draw top of tile
		else {
			char[] mapArr = tile.getTileName().getMapString().toCharArray();
			for (char c : mapArr) {
				displayGrid[xStart][y] = c;
				xStart++;
			}
		}
	}

	/**
	 * Draw the bottom of Island Tile
	 * @param mapChar The character that represents the tile state
	 * @param y    The row to draw the tile
	 * @param xMin The beginning of the bottom of Tile
	 * @param xMax The end of the bottom of Tile
	 */
	private void drawBottom(char mapChar, int y, int xMin, int xMax) {
		for (int x = xMin; x < xMax + 1; x++) {
			displayGrid[x][y] = mapChar;
        }
	}

	/**
	 * Draw the side of Island Tile
	 * @param mapChar The character that represents the tile state
	 * @param x    The column to draw the tile
	 * @param yMin The beginning of the side of Tile
	 * @param yMax The end of the side of Tile
	 */
	private void drawSide(char mapChar, int x, int yMin, int yMax) {
		for (int y = yMin; y < yMax + 1; y++) {
			displayGrid[x][y] = mapChar;
        }
	}

	/**
	 * Place the pawn on the grid.
	 * @param tile The IslandTile that the pawn is currently on.
	 * @param c    The pawn character
	 */
	public void setPawnOnGrid(IslandTile tile, char c) {
		// Get position x and y of Island tile
		int x = tile.getLoc().getX();
		int y = tile.getLoc().getY();

		// Translating tile position to display grid location
		int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
		int pawnNum = (int) c - 48; // Convert char to int (ASCII)

		// Logic to make sure the player pawns are displayed on different locations on a tile
		displayGrid[bL[0]+1+pawnNum][bL[1]+1+(pawnNum%2)] = c;
	}

	/**
	 * Removes the pawn character from the previous tile location.
	 * @param tile The IslandTile that the pawn is currently on.
	 * @param c    The pawn character
	 */
	public void resetPawnChar(IslandTile tile, char c) {
		// Get position x and y of Island tile
		int x = tile.getLoc().getX();
		int y = tile.getLoc().getY();

		// Translating tile position to display grid location
		int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
		int pawnNum = (int) c - 48; // Convert char to int (ASCII)
		
		// Reset previous pawn location on grid
		displayGrid[bL[0]+1+pawnNum][bL[1]+1+(pawnNum%2)] = ' ';
	}

	/**
	 * Resets the Treasure character for a treasure tile that is sunk.
	 * @param tile The tile to draw a corresponding treasure
	 */
	public void resetTreasureChar(IslandTile tile) {
		// Get position x and y of Island tile
		int x = tile.getLoc().getX();
		int y = tile.getLoc().getY();
		int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
		int[] tLoc = {bL[0] + 1, bL[1] + 1}; // Position of mapChar corresponding to the Treasure

		displayGrid[tLoc[0]][tLoc[1]] = ' ';
   }
  
    //-----------------------------------
    // Getters
    //----------------------------------- 
    /**
     * returns the char array of grid to be displayed.
     * @return the char array.
     */
    public char[][] getDisplayGrid() {
        return displayGrid;
    }

    //-----------------------------------
    // Other
    //-----------------------------------
    /**
     * Void function to print the grid with the island tiles, pawns, treasures etc.
     * Called by the Board class.
     */
    public void printGrid() { // Print the Grid to the terminal
        
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
    	theGrid = null;
    }
}