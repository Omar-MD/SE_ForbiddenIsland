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
	// Setup Singleton instance 
    private static WinObserver instance = null;
    private PlayerList playerList;

    //-----------------------------------
    // Get Instance of Singleton
    //-----------------------------------
    /**
     * getInstance method returns single instance of WinObserver.
     * @return instance singleton WinObserver object.
     */
    public static WinObserver getInstance() {
    	if(instance == null)
    		instance = new WinObserver();
    	return instance;
    }

    //------------------------
	// Constructor
	//------------------------
	/**
	 * WinConditions Constructor.
	 * Initializes observer and attaches subjects. 
	 */
    private WinObserver(){
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
            if(!p.getPawn().getPawnTile().getTileName().equals(TilesEnums.FOOLS_LANDING))
                return false;
        return true;
    }

    /**TODO: gameWin() Message
	 * Method executed if the team wins the game.
	 */
    private void gameWin(){
    	gameController.setGameFinish(true);
        System.out.println("***********------------   GAME WIN   ------------***********");
    }
}