package forbiddenIsland.setup;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.gameplay.WaterMeter;

/**
 * WaterMeter setup class for creating the Water Meter in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class WaterMeterSetup {
    
	//--------------------------------------
	// Variable Setup
	//--------------------------------------
    private Boolean valid_difficulty;
    private WaterMeter  setupWaterMeter;
    private List<String> difficulty = Arrays.asList("Novice","Normal","Elite","Legendary");

	//--------------------------------------
	// Constructor
	//--------------------------------------
	/**
	 * Constructor for WaterMeterSetup class
	 */
	public WaterMeterSetup() {
        // Get first instance of WaterMeter
        this.setupWaterMeter = WaterMeter.getInstance();
        // Initialize game difficulty check
        this.valid_difficulty = false;
	}

	//--------------------------------------
	//  Methods
	//--------------------------------------
	/**
	 * Sets the starting WaterMeter water Level in the Forbidden Island game.  
	 */
	protected void setupWaterLevel(Scanner user) {
		int gameDifficulty=0;
		boolean difficultySelected = false;
		while(!difficultySelected){
			while (!valid_difficulty) {
				gameDifficulty = getGameDifficulty(user);
			}
			setupWaterMeter.setWaterLevel(difficulty.get(gameDifficulty));
			difficultySelected=true;
			System.out.println("Water Level is at " + setupWaterMeter.getWaterLevel() + "\n");
		}
	}

	/**
	 * Gets starting Game difficulty from user.
	 * @param user 	The scanner the user which we read the users input from
	 * @return 		Game difficulty index
	 */
	public int getGameDifficulty(Scanner user) {
		String userString;
        System.out.println("\nWhat game difficulty would you like to play? (Must be between 1 and 4)");
        System.out.println("\nNovice: 1, Normal: 2, Elite: 3, Legendary: 4");
		userString = user.nextLine();
		return setGameDifficulty(userString);
	}

	/**
	 * Helper method, Parses user string for starting game difficulty.
	 * @param userString	 The user String containing game difficulty.
	 * @return 				 The game difficulty index to set the WaterLevel.
	 */
	public int setGameDifficulty(String userString) {
		int game_difficulty = 0;
		try {
			game_difficulty = Integer.parseInt(userString);
		} catch (NumberFormatException e) {
			return game_difficulty;
        }
        
		if ((game_difficulty >= 1) && (game_difficulty <= 4)) {
			valid_difficulty = true;
		}
		else{
			System.out.println("Incorrect input\n");
		}
		return (game_difficulty-1);
	}

	// Needed for testing
	public boolean getValidDifficulty(){
		return valid_difficulty;
	}
}
