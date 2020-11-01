package forbiddenIsland.board;

/**
 * Class for (X,Y) position on the Board in a game of Forbidden Island
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
	 * Returns the X coordinate of the position
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
	 * Sets the X coordinate of the position
     * @param x the X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
	 * Returns the Y coordinate of the position
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
	 * Sets the Y coordinate of the position
     * @param y the Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
	 * Returns string representing the Position object
	 * @return the string representation in (X,Y) coordinate form
	 */
    @Override
	public String toString() {
	    return "Position: (" + getX() + ","+ getY() + ")\n";
	}
}
