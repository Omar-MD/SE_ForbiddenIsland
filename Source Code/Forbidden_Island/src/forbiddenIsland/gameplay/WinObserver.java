package forbiddenIsland.gameplay;

import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * Win Observer class to monitor Winning conditions within the Forbidden Island game.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class WinObserver extends Observer{

    //------------------------
	// Variables
    //------------------------
    private PlayerList playerList;
    
    //------------------------
	// Constructor
	//------------------------
	/**
	 * WinConditions Constructor.
	 * Initializes observer and attaches subjects. 
	 */
    public WinObserver(){
        this.gameController = GameController.getInstance();
        this.gameController.attach(this);

        this.playerList = PlayerList.getInstance();
    }

    //------------------------
	//  Winning Conditions
    //------------------------
    @Override
    /**
	 * Check if playing team meets game winning conditions.
	 */
    public boolean update() {
        // Check if all treasures are captured
        if(playerList.getCapturedTreasure().size()==4){
            // Check if all players are on Fool's Landing
            if(isAllAtFoolsLanding()){
                gameWin();
                return true;
            }
        }
        return false;
    }

    /**
	 * Checks if all player pawns are on Fool's Landing.
	 * @return true if that is the case, false otherwise.
	 */
    private boolean isAllAtFoolsLanding(){
        for(Player p:playerList.getAllPlayers())
            if(!p.getPawn().getPawnTile().getTileName().name().equals(TilesEnums.FOOLS_LANDING.toString()))
                return false;
        return true;
    }

    /**TODO: gameWin() Message
	 * Method executed if the team wins the game.
	 */
    private void gameWin(){
        System.out.println("-------------WIN----------");
    }
}