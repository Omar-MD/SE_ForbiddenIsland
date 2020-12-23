package forbiddenIsland.board;

/**
 * Class for (X,Y) position on the Board in a game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class Position {

    //-----------------------------------
    // Variable Setup
    //-----------------------------------
	private int x;
	private int y;
	
    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor for Position object.
     * @param x X coordinate
     * @param y Y coordinate
     */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

    //-----------------------------------
    // Getters and Setters
    //-----------------------------------
	/**
	 * Returns the X coordinate of the position.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
	 * Sets the X coordinate of the position.
     * @param x X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
	 * Returns the Y coordinate of the position.
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
	 * Sets the Y coordinate of the position.
     * @param y Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
	 * Returns the Manhattan distance between two positions.
	 * @param pos  Position to calculate the distance against
     * @return     Manhattan distance
     */
    public int getManhattan(Position pos) {
        return Math.abs(x-pos.getX())+Math.abs(y-pos.getY());
    }

    /**
     * Returns boolean to check if positions are equal.
     * @return boolean
     */
    @Override
	public boolean equals(Object o) {
		// Check if o is an instance of Position
		if(o instanceof Position) {
			// Typecast o to Position so that we can compare data members
			Position p = (Position) o;
			// Compare the data members and return accordingly
			return x == p.getX() && y == p.getY();
		}
		return false;
	}

    /**
	 * Returns string representing the Position object.
	 * @return string representation in (X,Y) coordinate form
	 */
    @Override
	public String toString() {
	    return "Position: (" + getX() + ","+ getY() + ")\n";
	}
}
