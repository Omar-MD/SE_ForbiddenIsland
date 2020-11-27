package forbiddenIsland.gameplay;

import java.util.Scanner;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.enums.TilesEnums;

public class TileOnBoard {
    //------------------------
	// Variables
	//------------------------
    private Scanner inputScanner;
    private boolean    validTile;
    private String    tileString;
    
    //------------------------
	// Constructor
	//------------------------
    public TileOnBoard(Scanner user){
        this.inputScanner = user;
        this.validTile = false;
        this.tileString = null;
    }

    public void findTile(){
        // Obtain Valid Tile String
        while(!validTile){
            // Receive user input
            String userString = inputScanner.nextLine();  

            // Find referenced tile
            for(TilesEnums tileEnum:TilesEnums.values()){
                if(tileEnum.getMapString().equals(userString)){
                    validTile = true;
                }
            }
            // Check if valid Tile
            if(validTile){ System.out.println("\nValid Tile character chosen");}
            else { System.out.println("\nIncorrect Tile character chosen "); continue; }
        }
    }

    public String getTileString(){
        findTile();
        return this.tileString;
    }
    
    public boolean isValidTile(){
        return this.validTile;
    }

    public IslandTile getValidTile(){
        return Board.getInstance().getIslandTile(getTileString());
    }
}
