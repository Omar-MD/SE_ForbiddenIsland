package forbiddenIsland.gameplay;

import java.util.ArrayList;
import java.util.List;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.adventurer.Pilot;
import forbiddenIsland.card.FloodDeck;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.StateEnums;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.enums.TreasureEnums;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;
import forbiddenIsland.view.*;

/**
 * GameController class interacts with the Model and the Views to perform actions.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class GameController {

	//----------------------------
	// Variables
	//----------------------------
	// Setup Singleton instance 
	private static GameController instance;
	private Board                 board;
	private FloodDeck             floodDeck;
	private TreasureDeck          treasureDeck;
	private WaterMeter            waterMeter;
	private boolean               gameFinish;

	// Observers 
	public List<Observer> observers = new ArrayList<Observer>();

	//-----------------------------------
	// Get Instance of Singleton
	//-----------------------------------
	/**
	 * getInstance method returns singleton instance of GameController.
	 * @return singleton GameController object
	 */
	public static GameController getInstance() {
		if(instance == null)
			instance = new GameController();
		return instance;
	}

	//----------------------------
	// Constructor
	//----------------------------
	/**
	 * Constructor for GameController.
	 */
	private GameController() {
		this.board        = Board.getInstance();
		this.floodDeck    = FloodDeck.getInstance();
		this.treasureDeck = TreasureDeck.getInstance();
		this.waterMeter   = WaterMeter.getInstance();
		this.gameFinish   = false;
	}    

	//----------------------------
	// Methods
	//----------------------------
	/**
	 * Draw a treasure card from treasure deck as per player turn.
	 */
	public void drawTreasureCard(Player player){
		Card drawnCard = treasureDeck.drawCard();

		if(drawnCard.getName().equals(SpecialCardEnums.WATERS_RISE)){
			printout("Waters Rise card has been drawn");
			waterMeter.raiseWaterLevel();
			treasureDeck.discard(drawnCard);
			notifyAllObservers(); // Check if WaterLevel at Skull & Bones
		}
		else{
			player.getHand().addCard(drawnCard);
		}
	}

	/**
	 * Returns the drawn flood cards corresponding to the water level.
	 * @return     drawn flood cards
	 */
	public List<Card> drawFloodCards(){
		List<Card> drawnFloodCards = floodDeck.drawCard(waterMeter.getCardsToDraw());
		return drawnFloodCards;
	}

	/**
	 * Discard input card from treasure deck and remove from input player deck.
	 */
	public void discardChosenCard(Player player, Card card){
		// Discard card
		treasureDeck.discard(card);
		player.getHand().getDeck().remove(card);
	}

	/**
	 * Change the state of the Island Tiles matching the drawn flood cards. 
	 * From DRY to Flooded, or from Flooded to SUNK.
	 * Allow the players to escape from sinking tiles and notify observers.
	 */
	public void flipIslandTiles(List<Card> floodcards){
		for(Card c:floodcards){
			TilesEnums tileName = (TilesEnums) c.getName();
			board.getIslandTile(tileName).flip();
		}
		// Check if there are sinking players and try escape
		for(Player p:PlayerList.getInstance().getAllPlayers()){
			if(p.getPawn().getPawnTile().isSunk()){
				PlayerView playerView = new PlayerView(p);
				playerView.tryEscapeSinkingTile();
			}
		}
		// Notify LoseObserver, Check if Fools Landing Sunk or Player cannot escape sinking tile
		notifyAllObservers();
	}

	/**
	 * Printout the Character representation of each State.
	 */
	public void showStateNames() {
		List<String> stateNames = new ArrayList<String>();
		for(StateEnums state:StateEnums.values()){
			stateNames.add("\n["+state.getChar()+"]"+" : "+state.toString());
		}
		printout(String.join(" ", stateNames));
	}

	/**
	 * Printout the Character representation of each Treasure.
	 */
	public void showTreasureNames() {
		List<String> treasureNames = new ArrayList<String>();
		for(TreasureEnums treasure:TreasureEnums.values()){
			treasureNames.add("\n["+treasure.getChar()+"]"+" : "+treasure.toString());
		}
		printout(String.join(" ", treasureNames));
	}

	/**
	 * Printout the String representation of each Tile.
	 */
	public void showTileNames() {
		List<String> tileNames = new ArrayList<String>();
		for(TilesEnums tile:TilesEnums.values()){
			tileNames.add("\n["+tile.getMapString()+"]"+" : "+tile.toString());
		}
		printout(String.join(" ", tileNames));
	}

	/**
	 * Send board print request to Board object to print board on screen.
	 */
	public void showBoard(){
		// Check if board is visible
		board.printBoard();
	}

	//----------------------------
	// Player actions
	//----------------------------
	/**
	 * Send request to Player class to move player pawn to input tile.
	 * @param player   Player 
	 * @param moveTile New player pawn Tile
	 * @return boolean True if successful, false otherwise
	 */
	public boolean requestMove(Player player, IslandTile moveTile){
		return player.move(moveTile);
	}

	/**
	 * Send request to Player class to shore up an input tile.
	 * @param player      Player
	 * @param shoredTile  Island Tile to be shored up
	 * @return boolean    True if successful, false otherwise
	 */
	public boolean requestShoreUp(Player player, IslandTile shoredTile){
		return player.shoreUp(shoredTile);
	}

	/**
	 * Send request to Player class to give an input treasure card to an input player.
	 * @param player   Player giving card
	 * @param card     TreasureCard
	 * @param teamMate Player receiving card
	 * @return boolean True if successful, false otherwise
	 */
	public boolean requestGiveTreasureCard(Player player, TreasureCard card, Player teamMate){
		return player.giveTreasureCard(card, teamMate);
	}

	/**
	 * Send request to Player class to capture treasure.
	 * @param player   Player 
	 * @return boolean True if successful, false otherwise 
	 */
	public boolean requestCaptureTreasure(Player player){
		return player.captureTreasure();
	}

	/**
	 * Send request to Player class to use a Sandbags card.
	 * @param player      Player
	 * @param tile  	  Island Tile to be shored up
	 * @return boolean    True if successful, false otherwise
	 */
	public boolean requestUseSandbagsCard(Player player, IslandTile tile){
		return player.useSandbagsCard(tile);
	}

	/**
	 * Send request to Player class to use a Helicopter Lift card.
	 * @param player   		Player using Card
	 * @param flyingPlayer  list of flying players
	 * @param tile			Island Tile destination
	 * @return boolean      True if successful, false otherwise
	 */
	public boolean requestUseHelicopterLiftCard(Player player, List<Player> flyingPlayers, IslandTile tile){
		return player.useHelicopterLiftCard(flyingPlayers, tile);
	}

	/**
	 * Send request to Player class to escape sinking tile by flying.
	 * Note we must have a Player with a pilot role.
	 * @param player   Pilot player adventurer
	 * @param tile	   Island Tile destination
	 * @return boolean True if successful, false otherwise
	 */
	public boolean requestEscapeSinkingTileByFlight(Player player, IslandTile tile){
		Pilot pilot = (Pilot) player.getRole();
		return pilot.fly(player.getPawn(), tile);
	}

	/**
	 * Send request to Player class to escape sinking tile by swimming.
	 * @param player   Player swimming
	 * @param tile 	   Island Tile destination
	 * @return boolean True if successful, false otherwise
	 */
	public boolean requestEscapeSinkingTileBySwim(Player player, IslandTile tile){
		return player.swim(tile);
	}

	/**
	 * Return game finish status.
	 * @return boolean
	 */
	public boolean getGameFinish(){
		return this.gameFinish;
	}

	/**
	 * Set boolean gameFinish.
	 * @param boolean bool
	 */
	public void setGameFinish(boolean bool) {
		this.gameFinish = bool;
	}

	/**
	 * clean printout function to print to the console.
	 * @param toPrint String to be printed
	 */
	private void printout(String toPrint) {
		System.out.println(toPrint);
	}

	//-----------------------
	// Observer Methods
	//-----------------------
	/**
	 * Adds new observer to subject.
	 * @param ob	New Observer
	 */
	public void attach(Observer ob){
		observers.add(ob);
	}
	/**
	 * Removes observer from subject.
	 * @param ob	Observer
	 */
	public void detach(Observer ob){
		observers.remove(ob);
	}
	/**
	 * Notify all observers of subject change.
	 */
	public void notifyAllObservers(){
		for(Observer ob:observers){
			ob.update();
		}
	}
}

