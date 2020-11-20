package forbiddenIsland.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class depicting PlayerList in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class PlayerList {

	//------------------------
	// Setup Singleton PlayerList instance
	//------------------------
	private static PlayerList thePlayerList = null;
	private List<Player> playerList;

	//-----------------------------------
	// Get Instance of Singleton
	//-----------------------------------
	/**
	 * getInstance method returns single instance of PlayerList.
	 * @return instance singleton PlayerList object.
	 */
	public static PlayerList getInstance() {
		if( thePlayerList== null)
			thePlayerList = new PlayerList();
		return thePlayerList;
	}

	//----------------------------------
	// Constructor
	//----------------------------------
	/**
	 * Constructor for PlayerList object.
	 */
	private PlayerList() {
		playerList = new ArrayList<Player>();
	}

	//----------------------------------
	// Getter
	//----------------------------------
	
	/**
	 * Method returns list of players in game.
	 * @return Integer Number of players.
	 */
	public int getNumPlayers() {
		return this.playerList.size();
	}
	
	/**
	 * Method returns list of players in game.
	 * @return playerList 	Player List
	 */
	public List<Player> getAllPlayers() {
		return this.playerList;
	}
	
    /**
     * Method returns Player's index in the PlayerList.
     * @param player	The player of interest
     * @return 			Player's integer index.
     */
    public int getPlayerIndex(Player player){
    	return playerList.indexOf(player)+1;
    }

	/**
	 * Method returns Player from list.
	 * @param index		Index of Player
	 * @return 			Player at index.
	 */
	public Player getPlayer(int index) {
		return this.playerList.get(index-1);
	}
	
	//----------------------------------
	// Setter
	//----------------------------------
    
    /**
	 * Method used for adding a new player to list.
	 * @param player	Added player
	 */
	public void addPlayer(Player player) {
		playerList.add(player);
	}
}
