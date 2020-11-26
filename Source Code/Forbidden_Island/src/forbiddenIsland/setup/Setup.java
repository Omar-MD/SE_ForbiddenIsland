package forbiddenIsland.setup;

import java.util.Scanner;

/**
 * Singleton Facade to handle all required setup for a game of Forbidden Island.
 *
 * @author  Jithin James and Omar Duadu
 * @version 1.0
 */

public class Setup {

    //===========================================================
    // Variable Setup
    //===========================================================
	// Self singleton
    private static Setup theSetup;

    // Setup Controllers
    private PlayerSetup playerHandler;
    private CardSetup   cardHandler;
    private BoardSetup  boardHandler;
    private WaterMeterSetup waterMeterHandler;

    //===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance of singleton Setup class
     * @return instance of Setup class
     */
    public static Setup getInstance(){
        if(theSetup == null){
            theSetup = new Setup();
        }
        return theSetup;
    }

    //===========================================================
    // Constructor
    //===========================================================
    /**
     * Constructor class for Setup
     */
    private Setup() {
        this.playerHandler      = new PlayerSetup();
        this.cardHandler        = new CardSetup();
        this.boardHandler       = new BoardSetup();
        this.waterMeterHandler  = new WaterMeterSetup();
    }

    //===========================================================
    // Setup Methods
    //===========================================================
    /**
     * Handles all setup
     */
    public void doAllSetup(Scanner user) {

    	welcomeScreen();

        boardHandler.initialiseBoard();

        cardHandler.drawFloodCards();

        playerHandler.createAllPlayers(user);

        //TODO: boardHandler.addPlayerPawns();

        cardHandler.drawTreasureCards();

        waterMeterHandler.setupWaterLevel(user);

    }

    /**
     * Printout a Welcome / start screen
     * TODO: Needs more work.
     */
    public void welcomeScreen(){
        System.out.println("Welcome to Forbidden Island!");
        System.out.println("\nSoftware Engineering (COMP 41670) 2020\n");
        System.out.println("Authors: Jithin James and Omar Duadu\n");
    }
}
