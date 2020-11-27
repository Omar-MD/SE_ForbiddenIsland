package forbiddenIsland.gameplay;

import java.util.Scanner;

import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

public class PlayerInGame {
    //------------------------
	// Variables
	//------------------------
    private boolean    validPlayer;
    private int        playerIndex;
    
    //------------------------
	// Constructor
	//------------------------
    public PlayerInGame(){
        this.validPlayer = false;
        this.playerIndex = 0;
    }

    public void findPlayer(Scanner inputScanner){
        // Obtain Valid Tile String
        while(!validPlayer){
            // Receive user input
            String userString = inputScanner.nextLine();

            // Find referenced player
            for(Player teamMate: PlayerList.getInstance().getAllPlayers()){
                if(teamMate.getName().equals(userString)){ 
                    validPlayer = true; 
                    playerIndex = PlayerList.getInstance().getPlayerIndex(teamMate);
                }
            }
            // Check if valid player
            if(validPlayer){ System.out.println("\nValid Player chosen");}
            else { System.out.println("\nIncorrect Player chosen "); continue; }
        }
    }

    public int getPlayerIndex(){
        return this.playerIndex;
    }
    
    public boolean isValidPlayer(){
        return this.validPlayer;
    }

    public Player getValidPlayer(){
        return PlayerList.getInstance().getPlayer(playerIndex);
    }
    
}
