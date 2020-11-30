package forbiddenIsland.setup;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * PlayerSetup class for creating the players in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class PlayerSetup {

	//--------------------------------------
	// Variable Setup
	//--------------------------------------
	private PlayerList playerList;
	private Boolean validNumPlayers = false;
	private List<String> roles = new ArrayList<>(Arrays.asList("Pilot","Navigator","Messenger","Engineer","Explorer","Diver"));

	//--------------------------------------
	// Constructor
	//--------------------------------------
	/**
	 * Constructor for PlayerSetup class
	 */
	public PlayerSetup() {
		this.playerList = PlayerList.getInstance();
	}

	//--------------------------------------
	//  Methods
	//--------------------------------------
	/**
	 * Creates the players who will play the game
	 */
	protected void createAllPlayers(Scanner user) {
		int numOfPlayers;
		boolean playersSelected = false;

		while (!playersSelected) {
			numOfPlayers = 0;
			while (!validNumPlayers) {
				numOfPlayers = getNumberOfPlayers(user);
			}
			for (int i = 0; i < numOfPlayers; i++) {
				createIndividualPlayer(user, i);
			}
			playersSelected = true;
		}
	}

	/**
	 * Gets Number of Players who are playing.
	 * @param user 	The scanner the user which we read the users input from
	 * @return 		The number of Players to create
	 */
	public int getNumberOfPlayers(Scanner user) {
		String userString;
		System.out.println("\nHow many people are playing? (must be between 2 and 4)");
		userString = user.nextLine();
		return setNumPlayers(userString);
	}

	/**
	 * Helper method, Parses user string for number of players.
	 * @param userString	 The user String containing number of players.
	 * @return 				 The number of Players to create.
	 */
	private int setNumPlayers(String userString) {
		int numOfPlayers =0;
		try {
			numOfPlayers = Integer.parseInt(userString);
		} catch (NumberFormatException e) {
			return numOfPlayers;
		}

		if ((numOfPlayers >= 2) && (numOfPlayers <= 4)) {
			validNumPlayers = true;
		}
		else{
			System.out.println("Incorrect input\n");
		}
		return numOfPlayers;
	}

	/**
	 * Assign role to a player, create Player and add Player to PlayerList
	 * @param user	Scanner which we read the users input from
	 * @param i     Integer value representing the player number
	 */
	public void createIndividualPlayer(Scanner user, int i) {
		System.out.println("\nPlayer "+(i+1)+"...");
		System.out.println("Enter your name:");
		String name = user.nextLine();

		//Randomly assign adventurer role
		Collections.shuffle(roles);
		String role = roles.remove(0);
		System.out.println(name + " is playing as " + role + "\n");
		// create Player and add them to PlayerList
		// want players to be from 1-4 rather than 0-3
		playerList.addPlayer(new Player(i+1, name, role));
	}
	
}

