package forbiddenIsland.board;

import forbiddenIsland.enums.*;

/**
 * Class for Island Tiles on the Board in a game of Forbidden Island
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class IslandTile {
    //-----------------------------------
    // Variable Setup
    //-----------------------------------
    private TilesEnums tileName;
    private Position loc;
    private StateEnums state;
    private TreasureEnums treasure;

    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor for an Island Tile object.
     * @param tileName The name of the Island Tile
     * @param loc Position of the Island Tile
     * @param state State of the Island Tile
     */
    public IslandTile(TilesEnums tileName, Position loc, StateEnums state) {
        this.tileName = tileName;
        this.loc = loc;
        this.state = state;
        this.treasure = checkForTreasure();
    }

    //-----------------------------------
    // Methods
    //-----------------------------------
    /**
     * Get TreasureEnum from an Island Tile
     * @return The TreasureEnum associated with the Island Tile or null
     * if no TreasureEnum is associated.
     */
	private TreasureEnums checkForTreasure() {
		if (tileName.toString().contains("Temple")) {
			return TreasureEnums.THE_EARTH_STONE;
		}
		else if (tileName.toString().contains("Garden")) {
			return TreasureEnums.THE_STATUE_OF_THE_WIND;
		}
		else if (tileName.toString().contains("Cave")) {
			return TreasureEnums.THE_CRYSTAL_OF_FIRE;
		}
		else if (tileName.toString().contains("Palace")) {
			return TreasureEnums.THE_OCEANS_CHALICE;
		}
		else {
			return null;
		}
	}

    //-----------------------------------
    // Getters and Setters
    //-----------------------------------
    /**
     * Returns the Island Tile name
     * @return the tile name
     */
    public TilesEnums getTileName() {
        return tileName;
    }

    /**
     * Sets the Island Tile name
     * @param tileName the tile name
     */
    public void setTileName(TilesEnums tileName) {
        this.tileName = tileName;
    }

    /**
	 * Returns the position of the Island Tile
     * @return the position
     */
    public Position getLoc() {
        return loc;
    }

    /**
	 * Sets the position of the Island Tile
     * @param loc the Position object
     */
    public void setLoc(Position loc) {
        this.loc = loc;
    }

    /**
	 * Returns the Island Tile state
     * @return the tile state
     */
    public StateEnums getState() {
        return state;
    }

    /**
	 * Sets the Island Tile state
     * @param state the tile state
     */
    public void setState(StateEnums state) {
        this.state = state;
        setTileOutline();
    }

    /**
	 * Returns the Treasure enum associated with the Island Tile or null
     * @return the treasure
     */
    public TreasureEnums getTreasure() {
        return treasure;
    }

    /**
	 * Sets the Treasure enum associated with the Island Tile or null
     * @param treasure the treasure
     */
    public void setTreasure(TreasureEnums treasure) {
        this.treasure = treasure;
    }

    //----------------------------------------------------------
    // Helper methods to draw/update Island tiles on the Board
    //----------------------------------------------------------
    /**
	 * Outline the tile to represent in the form of a grid.
	 */
	public void setTileOutline() {
		// Get position x and y of Island tile
		int x = getLoc().getX();
		int y = getLoc().getY();

		// Translating tile position to display grid location
		int[] bL = {8*x, 4*y}; // Bottom left corner of a tile
		int[] bR = {bL[0] + 6, bL[1]}; // Bottom right corner of a tile
		int[] tL = {bL[0], bL[1] + 3}; // Top left corner of a tile
		int[] tR = {bL[0] + 6, bL[1] + 3}; // Top right corner of a tile		

		// mapChar to represent tile state i.e. dry, flooded or sunk
		char mapChar = getState().getChar();
		drawSide(mapChar, bL[0],bL[1] + 1,tL[1]);   // Left side of tile
		drawSide(mapChar, bR[0],bR[1] + 1,tR[1]);   // Right side of tile
		drawBottom(mapChar, bL[1],bL[0],bR[0]); // Bottom of tile
		drawTop(tL[1], tL[0] + 2); // Top of tile
	}

	/**
	 * Draw the top of Island Tile
	 * @param y The row to draw the tile
	 * @param xStart The beginning of the top of Tile
	 */
	private void drawTop(int y, int xStart) {
		Board theBoard = Board.getInstance();
		// If tile is sunk, replace top of tile with space chars
		if (isSunk()) {
			for (int x = xStart; x < xStart + 3; x++) {
				theBoard.getDisplayGrid()[x][y] = ' ';
			}
		}
		// If tile is not sunk, draw top of tile
		else {
			char[] mapArr = getTileName().getMapString().toCharArray();
			for (char c : mapArr) {
				theBoard.getDisplayGrid()[xStart][y] = c;
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
		Board theBoard = Board.getInstance();
		for (int x = xMin; x < xMax + 1; x++) {
			theBoard.getDisplayGrid()[x][y] = mapChar;
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
		Board theBoard = Board.getInstance();
		for (int y = yMin; y < yMax + 1; y++) {
			theBoard.getDisplayGrid()[x][y] = mapChar;
        }
	}

    /**
	 * Returns true if Island tile is sunk and false otherwise
	 * @return boolean
	 */
    public boolean isSunk() {
        return this.getState() == StateEnums.SUNK;
    }
    
    /**
	 * Returns string representing the Island Tile object
	 * @return the string representation
	 */
    @Override
	public String toString() {
	    return "Island Tile: " + tileName.toString() + "\n" +
	            "Treasure: " + getTreasure() + "\n" +
	    		"State: " + state.toString() + "\n" +
	            loc.toString();
	}
}
