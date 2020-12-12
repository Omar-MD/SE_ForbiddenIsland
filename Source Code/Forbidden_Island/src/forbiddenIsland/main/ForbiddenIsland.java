package forbiddenIsland.main;

import forbiddenIsland.gameplay.GameController;
import forbiddenIsland.gameplay.LoseObserver;
import forbiddenIsland.gameplay.WinObserver;
import forbiddenIsland.setup.Setup;
import forbiddenIsland.view.GameView;

import java.util.Scanner;

public class ForbiddenIsland {

	public static void main(String[] args) {

		Scanner inputScanner = new Scanner(System.in);

		Setup.getInstance().doAllSetup(inputScanner);

		// Setting up the Observers
        WinObserver.getInstance(); 
	    LoseObserver.getInstance();

        // Get the instance of the controller object
        GameController controller =  GameController.getInstance();
	    
        // Get the instance of the View Object
	    GameView gameView = GameView.getInstance();

	    // Set the game controller to the game view
		gameView.setController(controller);

		// Start the View
		gameView.run(inputScanner);

		inputScanner.close();
    	
	}
}
