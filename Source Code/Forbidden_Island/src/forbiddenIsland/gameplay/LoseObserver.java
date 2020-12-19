package forbiddenIsland.gameplay;

import forbiddenIsland.board.Board;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * Lose Observer class to monitor losing conditions within the Forbidden Island game.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class LoseObserver extends Observer {

    //------------------------
	// Variables
    //------------------------
	// Setup Singleton instance 
    private static LoseObserver instance = null;
    private Board           board; 
    private WaterMeter waterMeter;
    private PlayerList playerList;

    //-----------------------------------
    // Get Instance of Singleton
    //-----------------------------------
    /**
     * getInstance method returns single instance of LoseObserver.
     * @return instance singleton LoseObserver object.
     */
    public static LoseObserver getInstance() {
    	if(instance == null)
    		instance = new LoseObserver();
    	return instance;
    }

    //------------------------
	// Constructor
	//------------------------
	/**
	 * LoseConditions Constructor.
	 * Initializes observer and attaches subjects. 
	 */
    private LoseObserver(){
        this.gameController = GameController.getInstance();
        this.gameController.attach(this);

        this.board           = Board.getInstance();
        this.waterMeter = WaterMeter.getInstance();
        this.playerList = PlayerList.getInstance();
    }

    //------------------------
	// Methods
	//------------------------
    @Override
    /**
	 * Automatically check if playing team meets game losing conditions.
     * Executed whenever an observed subject state changes.
	 */
    public boolean update() {
        // WaterMeter at 10
        if(isSkullAndBones()){
            System.out.println("WATER LEVEL AT SKULL & BONES");
            gameOver();
            return true;
        }
        // Fool's Landing sinks
        if(isFoolsLandingSunk()){
            System.out.println("FOOL'S LANDING SUNK");
            gameOver();
            return true;
        }
            
        // Treasure Tiles sink before treasure is captured
        if(isTreasureLost()){
            System.out.println("TREASURE TILE SUNK");
            gameOver();
            return true;
        }
        // Player pawn sunk
        if(playerDrowned()){
            System.out.println("PLAYER SUNK");
            gameOver();
            return true;
        }
        return false;
    }

    /**TODO: gameOver() Message 
	 * Method executed if the team loses the game.
	 */
    private void gameOver(){
    	gameController.setGameFinish(true);
    	System.out.println(" _____  _____  _____  _____    _____  _____  _____  _____");
        System.out.println("|   __||  _  ||     ||   __|  |     ||  |  ||   __|| __  |");
        System.out.println("|  |  ||     || | | ||   __|  |  |  ||  |  ||   __||    -|");
        System.out.println("|_____||__|__||_|_|_||_____|  |_____| \\___/ |_____||__|__|");
    }

    //------------------------
	// Helper Methods
	//------------------------
    /**
	 * Checks if water level is at Cross & Bones.
	 * @return true if that is the case, false otherwise.
	 */
    private boolean isSkullAndBones(){
        // Check if water level at 10
        if(waterMeter.getWaterLevel()==10)
            return true;
        return false;
    }

    /**
	 * Checks if Fool's Landing is sunk.
	 * @return true if that is the case, false otherwise.
	 */
    private boolean isFoolsLandingSunk(){
        // Check if Fool's Landing is Sunk
        if(board.getIslandTile(TilesEnums.FOOLS_LANDING).isSunk())
            return true;
        return false;
    }

    /**
	 * Checks if the treasure tiles sunk before the treasure was captured.
	 * @return true if that is the case, false otherwise.
	 */
    private boolean isTreasureLost(){
    	// Crystal of Fire 
    	if(!playerList.getCapturedTreasure().contains(new Treasure(TreasureEnums.THE_CRYSTAL_OF_FIRE))){
    		if(board.getIslandTile(TilesEnums.CAVE_OF_EMBERS).isSunk() && 
    				board.getIslandTile(TilesEnums.CAVE_OF_SHADOWS).isSunk()){
    			return true;
    		} 
    	}
    	// Earth Stone
    	if(!playerList.getCapturedTreasure().contains(new Treasure(TreasureEnums.THE_EARTH_STONE))){
    		if(board.getIslandTile(TilesEnums.TEMPLE_OF_THE_MOON).isSunk() && 
    				board.getIslandTile(TilesEnums.TEMPLE_OF_THE_SUN).isSunk()){
    			return true;
    		}
    	}
    	// Ocean's Chalice
    	if(!playerList.getCapturedTreasure().contains(new Treasure(TreasureEnums.THE_OCEANS_CHALICE))){
    		if(board.getIslandTile(TilesEnums.CORAL_PALACE).isSunk() && 
    				board.getIslandTile(TilesEnums.TIDAL_PALACE).isSunk()){
    			return true;
    		}
    	}
    	// Statue of Wind
    	if(!playerList.getCapturedTreasure().contains(new Treasure(TreasureEnums.THE_STATUE_OF_THE_WIND))){
    		if(board.getIslandTile(TilesEnums.WHISPERING_GARDEN).isSunk() && 
    				board.getIslandTile(TilesEnums.HOWLING_GARDEN).isSunk()) {
    			return true;
    		}
    	}
        return false;
    }

    /** 
	 * Checks if any of the players is on a Sunk tile and failed to escape.
	 * @return true if that is the case, false otherwise.
	 */
    private boolean playerDrowned(){
        // Check if any players Sunk
        for(Player p:playerList.getAllPlayers()){
            if(p.getPawn().getPawnTile().isSunk())
                return true;
        }
        return false;
    }
}