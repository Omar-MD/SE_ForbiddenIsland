package forbiddenIsland.card;

import java.util.List;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.gameplay.WaterMeter;
import forbiddenIsland.player.Player;

/**
 * SpecialCard class for special cards found in a Treasure Deck in the game of forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class SpecialCard extends Card{
	
    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor for a Special Card object.
     * @param cardName 	The name of the Card
     */
	public SpecialCard(SpecialCardEnums cardName){
		super(cardName);
	}
	
	//-----------------------------------
	// Methods
	//-----------------------------------
	/**
	 * Play the Waters Rise card. Increases the game water level.
	 */
	public void useWatersRise(){
		WaterMeter  WMeter = WaterMeter.getInstance();
		WMeter.raiseWaterLevel();
	}

	/**
	 * Use the Sandbags card. Use to shore up any Island Tile. 
	 * @param islandTile 	Island Tile to be shored up
	 * @return boolean      True if successful, false otherwise
	 */
	public boolean useSandbags(IslandTile islandTile){
		if (islandTile.isFlooded()) {
			islandTile.setState(StateEnums.DRY);
			System.out.println("Successfully shored up "+ islandTile.getTileName().toString());
			return true;
		}
		else if(islandTile.isDry()) {
			System.out.println("Error(useSandbags): Island Tile is already Dry");
			return false;
		}
		else if(islandTile.isSunk()) {
			System.out.println("Error(useSandbags): Island Tile is Sunk");
			return false;
		}
		return false;
	}

	/**
	 * Use the Helicopter lift card. Transports Player(s) to another Island Tile.
	 * If all team players are on Fool's landing, used to win the game.
	 * @param flyingPlayers	 List of Players to be transported
	 * @param newTile	 	 Island Tile destination
	 * @return boolean       True if successful, false otherwise
	 */
	public boolean useHelicopterLift(List<Player> flyingPlayers,IslandTile newTile){
		// Check if destination Island Tile is Sunk
		if(newTile.isSunk()) {
			System.out.println("\nError: Destination Island Tile is Sunk.");
			return false;
		}
		// Check if list of flyers is empty
		if(flyingPlayers.isEmpty()) {
			System.out.println("\nError: No Flying Players chosen.");
			return false;
		}
		// Move all players
		for(Player p: flyingPlayers) {
			p.getPawn().setPawnTile(newTile);
			System.out.println("Msg: Successfull transport to "+ newTile.getTileName().toString());
		}
		return true;
	}
}
