package forbiddenIsland.gameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.enums.TilesEnums;


/**
 * SelectTile class finds selected IslandTile on Board.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class SelectTile {
    //------------------------
	// Variables
	//------------------------
    private boolean         isValidTile;
    private List<IslandTile> validTiles;
    
    //------------------------
	// Constructor
	//------------------------
    public SelectTile(){
        this.isValidTile = false;
        // this.validTile = null;
        this.validTiles = new ArrayList<IslandTile>();
    }
    /**
     * Method finds selected Island Tiles from board.
     * @param Scanner User Input
     */
    public void findTile(Scanner inputScanner){
        // Obtain Valid Tile String
        while(!isValidTile){
            // Receive user input
            String userString = inputScanner.nextLine();  
            List<String> names = new ArrayList<String>(Arrays.asList(userString.split(" ")));

            // Find referenced players
            for(int j=0; j< names.size(); j++){
                for(TilesEnums tileEnum: TilesEnums.values()){
                    if(tileEnum.getMapString().equals(names.get(j))){
                        validTiles.add(Board.getInstance().getIslandTile(tileEnum));   
                    }
                }
                if(!validTiles.isEmpty()){
                    isValidTile = true;
                }
            }
            // Check if valid players
            if(isValidTile){ System.out.println("\nValid Island Tile(s)");}
            else { System.out.println("\nIncorrect Island Tile(s)"); continue; }
        }
    }

    /**
     * Return if selected tile is valid.
     * @return Boolean True or False
     */
    public boolean isValidTile(){
        return this.isValidTile;
    }

    /**
     * Returns list of selected Island Tiles.
     * @return List<IslandTile>
     */
    public List<IslandTile> getValidTiles(){
        return this.validTiles;
    }
    
}
