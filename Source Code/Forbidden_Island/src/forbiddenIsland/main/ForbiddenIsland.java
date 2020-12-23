package forbiddenIsland.main;

import forbiddenIsland.gameplay.GameController;
import forbiddenIsland.gameplay.LoseObserver;
import forbiddenIsland.gameplay.WinObserver;
import forbiddenIsland.setup.Setup;
import forbiddenIsland.view.GameView;

import java.util.Scanner;

public class ForbiddenIsland {

	public static void main(String[] args) {

		// User input
		Scanner inputScanner = new Scanner(System.in);

		// Game Setup
		Setup.getInstance().doAllSetup(inputScanner);

		// Observers Setup
        WinObserver.getInstance(); 									// Get WinObserer instance
	    LoseObserver.getInstance();									// Get LoseObserver instance

        // Model View Controller Setup
        GameController controller =  GameController.getInstance();	// Get GameController instance
	    GameView gameView = GameView.getInstance();					// Get GameView instance
		gameView.setController(controller); 	 					// Set the game controller to the game view

		// Run Game
		gameView.run(inputScanner);	// Start gameView

		// Close User input
		inputScanner.close();
	}
}
