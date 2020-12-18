package forbiddenIsland.view;

import java.util.Scanner;

import forbiddenIsland.gameplay.GameController;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;


/**
 * GameView singleton class manages all of the views.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class GameView {

    //----------------------------
	// Variables
	//----------------------------
	// Setup Singleton instance 
    private static GameView instance = null;
    private Scanner         inputScanner;
    private GameController  controller;

    //-----------------------------------
    // Get Instance of Singleton
    //-----------------------------------
    /**
     * getInstance method returns single instance of GameView.
     * @return instance singleton GameView object.
     */
    public static GameView getInstance() {
    	if(instance == null)
    		instance = new GameView();
    	return instance;
    }

  	//----------------------------
	// Constructor
	//----------------------------
    /**
     * Constructor for GameView.
     */
    private GameView() {
    	this.controller = GameController.getInstance();
    }   

    //----------------------------
	// Public Methods
	//----------------------------
    /**
     * Prepares the user interface of the program.
     */
    public void run(Scanner inputScanner) {
    	this.inputScanner = inputScanner;
        while (!controller.getGameFinish()) {
        	for (Player p: PlayerList.getInstance().getAllPlayers()) {
				PlayerView playerView = new PlayerView(p);
				playerView.doTurn();

				// Check and return if game is finished between player turns 
				if(controller.getGameFinish()) return;
			}
        }
    }

    //----------------------------
	// Private Methods
	//----------------------------
    /**
	 * Printout the options a player can make for their turn.
	 */
	protected void giveOptions() {
		printout("\nWhat do you want to do?\nYour options are:");
		printout("[1]    Move");
		printout("[2]    Shore Up");
		printout("[3]    Give a Treasure Card");
        printout("[4]    Capture a Treasure");
        printout("[5]    Use a Special Card");
        printout("[6]    Show Board");
        printout("[7]    Show Hand");
        printout("[8]    Show Help");
        printout("[9]    Team Play");
		printout("[0]    End turn");
    }

	/**
	 * Team Play lets you view your team member's hands and
	 * allow them to play a special card even during your turn.
	 */
	protected void giveTeamPlayOptions() {
		printout("\nChoose one of the following:");
		printout("[1]    View the hands of your team members");
        printout("[2]    Allow a team member to use a Special Card");
        printout("[3]    View the captured treasures");
		printout("[0]    Return to Main Options");
    }

	/**
	 * Option to view game help
	 * Tiles displays all the tile abbreviations in the game
	 * Tile States displays all the tile state characters in the game
	 * Treasures displays all the treasure abbreviations in the game
	 * Return brings you back out of the Help
	 */
	public void seeHelp(){
		int userInt;
		boolean returnCall = false;
		boolean validInput = false;
		
		// Continue looping until input is valid and return is requested by user
		while (!validInput || !returnCall) {
			printout("\nChoose from the options below:");
			printout("\n[1] Tiles \n[2] Tile States \n[3] Treasures \n[4] Return \n");
			String userString = inputScanner.nextLine();
			
			try {userInt = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {
            	printout("\n" + userString + " is not a valid input!");
            	continue;
            }

			if ((userInt >= 1) && (userInt <= 4)) {
				validInput = true;
			} else {
				validInput = false;
				printout("\n" + userInt + " is not a valid input!\n");
				continue;
			}

            switch (userInt) {
                case 1:  controller.showTileNames();     break;
                case 2:  controller.showStateNames();    break;
                case 3:  controller.showTreasureNames(); break;
                case 4:  returnCall = true;              break;
                default: printout("CASE ERROR IN GameView.seeHelp()");
            }
		}
	}

    //----------------------------
	// Private Helper Methods
    //----------------------------
    /**
	 * Return the input scanner
	 */
    public Scanner getScanner(){
        return inputScanner;
    }

    /**
     * @return the controller
     */
    public GameController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    //----------------------------
	// Clean Methods
    //----------------------------
    /**
	 * clean printout function to print to the console.
	 * @param toPrint The string to be printed.
	 */
	private void printout(String toPrint) {
		System.out.println(toPrint);
    }
}
