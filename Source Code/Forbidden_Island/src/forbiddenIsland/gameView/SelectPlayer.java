package forbiddenIsland.gameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * SelectPlayer class finds selected players from playerList.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class SelectPlayer {
    //------------------------
	// Variables
	//------------------------
    private boolean    isValidPlayer;
    private List<Player>     players;
    
    //------------------------
	// Constructor
	//------------------------
    public SelectPlayer(){
        this.isValidPlayer = false;
        this.players = new ArrayList<Player>();
    }
    /**
     * Find selected players.
     * @param Scanner User input
     */
    public void findPlayer(Scanner inputScanner){
        int playerIndex = 0;
        // Obtain List of Players
        while(!isValidPlayer){
            String userString = inputScanner.nextLine();
            List<String> names = new ArrayList<String>(Arrays.asList(userString.split(" ")));

            // Find referenced players
            for(int j=0; j< names.size(); j++){
                for(Player teamMate: PlayerList.getInstance().getAllPlayers()){
                    if(teamMate.getName().equals(names.get(j))){ 
                        playerIndex = PlayerList.getInstance().getPlayerIndex(teamMate);
                        players.add(PlayerList.getInstance().getPlayer(playerIndex));
                    }
                }
                if(!players.isEmpty()){
                    isValidPlayer = true;
                }
            }
            // Check if valid players
            if(isValidPlayer){ System.out.println("\nValid Players");}
            else { System.out.println("\nIncorrect Players"); continue; }
        }
    }
    /**
     * Returns if chosen players are valid.
     * @return Boolean True or false
     */
    public boolean isValidPlayer(){
        return this.isValidPlayer;
    }

    /**
     * Returns List of chosen players.
     * @return List<Player> 
     */
    public List<Player> getValidPlayer(){
        return this.players;
    }
    
}