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
    private TilesEnums    tileName;
    private Position      loc;
    private StateEnums    state;
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

    /**
	 * Changes the Island Tile state from DRY to FLOODED 
     * or from FLOODED to SUNK.
     */
    public void flip() {
        if(isDry())
            this.state = StateEnums.FLOODED;
        else if(isFlooded())
            this.state = StateEnums.SUNK;
        setTileOutline();
        //notifyAllObserver()
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
        Grid.getInstance().setTileOutline(this);
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

    //-----------------------------------
    // Other
    //-----------------------------------
    /**
	 * Returns true if Island tile is dry and false otherwise
	 * @return boolean
	 */
    public boolean isDry() {
        return getState().equals(StateEnums.DRY);
    }

    /**
	 * Returns true if Island tile is flooded and false otherwise
	 * @return boolean
	 */
    public boolean isFlooded() {
        return getState().equals(StateEnums.FLOODED);
    }

    /**
	 * Returns true if Island tile is sunk and false otherwise
	 * @return boolean
	 */
    public boolean isSunk() {
        return getState().equals(StateEnums.SUNK);
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
