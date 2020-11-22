package forbiddenIsland.card;

import java.util.List;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.gameplay.WaterMeter;
import forbiddenIsland.player.Player;

/**
 * SpecialCard class for special cards found
 * in a Treasure Deck in the game of forbidden Island.
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
	 * Method to play the Waters Rise card, increases the game water level.
	 */
	public void useWatersRise(){
		WaterMeter  WMeter = WaterMeter.getInstance();
		WMeter.raiseWaterLevel();
	}

	/**
	 * Use the ability of the Sandbags card, used to shore up any Island Tile. 
	 * @param islandTile 	Island Tile to be shored up.
	 * @return boolean      Boolean representing whether Sandbags card was used or not
	 */
	public boolean useSandbags(IslandTile islandTile){
		if (islandTile.isFlooded()) {
			islandTile.setState(StateEnums.DRY);
			System.out.println("Msg: Successfully shored up "+ islandTile.getTileName().toString());
			return true;
		}
		else if(islandTile.isDry()) {
			System.out.println("Error: Island Tile is already Dry");
			return false;
		}
		else if(islandTile.isSunk()) {
			System.out.println("Error: Island Tile is Sunk");
			return false;
		}
		else {
			return false;
		}
	}

	/**
	 * Use the ability of the Helicopter lift card, transports Player(s) to another Island Tile.
	 * Players must be on the same tile. 
	 * If all team players are on Fool's landing, used to reach the Finish.
	 * @param flyingPlayers	 List of Players to be transported
	 * @param newTile	 	 Island Tile destination.
	 * @return boolean       Boolean representing whether Helicopter Lift card was used or not
	 */
	public boolean useHelicopterLift(List<Player> flyingPlayers,IslandTile newTile){
		// Check if destination Island Tile is Sunk
		if(newTile.isSunk()) {
			System.out.println("Error: Destination Island Tile is Sunk.");
			return false;
		}
		// Ensure all flying members are on the same Island Tile.
		for(Player p: flyingPlayers) {
			if(!p.getPawn().isSameTile(flyingPlayers.get(0).getPawn())) {
				System.out.println("Error: All Flying players must be on the same Island Tile");
				return false;
			}
		}
		// Move all players
		for(Player p: flyingPlayers) {
			p.getPawn().setPawnTile(newTile);
			System.out.println("Msg: Successfull transport to "+ newTile.getTileName().toString());
			return true;
		}
		return false;
	}
}
