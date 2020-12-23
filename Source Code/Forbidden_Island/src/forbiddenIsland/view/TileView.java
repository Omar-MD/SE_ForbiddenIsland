package forbiddenIsland.view;

import java.util.Scanner;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.enums.TilesEnums;

/**
 * TileView class finds selected IslandTile on Board.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class TileView {

    //------------------------
	// Variables
	//------------------------
	private GameView   gameView;
    private Scanner    inputScanner;
    private boolean    isValidTile;
    private IslandTile validTile;
    
    //------------------------
	// Constructor
	//------------------------
    public TileView(){
    	this.gameView     = GameView.getInstance();
    	this.inputScanner = gameView.getScanner();
        this.isValidTile  = false;
    }

    //-----------------------
    // Methods
    //-----------------------
    /**
     * Method returns selected Island Tile from board.
     * @return selected IslandTile
     */
    public IslandTile findTile(){
    	while(!isValidTile){
    		// Receive user input
    		String userString = inputScanner.nextLine();

    		// Gets tile matching with input string
    		for(TilesEnums tileEnum: TilesEnums.values()){
    			if(tileEnum.getMapString().equals(userString.toUpperCase())){
    				validTile = Board.getInstance().getIslandTile(tileEnum);   
    			}
    		}

    		// If the selected tile exists, then set boolean to true
    		if(validTile != null){
    			isValidTile = true;
    			System.out.println("\nValid Island Tile");
    		} else {
    			System.out.println("\nIncorrect Island Tile. Please enter valid tile: (For Example: SIG)");
    		}
    	}
    	return validTile;
    }

    /**
     * Returns selected Island Tile.
     * @return the selected Island Tile
     */
    public IslandTile getValidTile(){
        return this.validTile;
    }
    
}
