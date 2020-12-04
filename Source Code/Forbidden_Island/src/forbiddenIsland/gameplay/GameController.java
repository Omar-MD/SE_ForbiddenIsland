package forbiddenIsland.gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.adventurer.Engineer;
import forbiddenIsland.adventurer.Pilot;
import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.gameView.*;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * GameController class performs all of the actions that a player can make.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class GameController {
    
    //----------------------------
	// Variables
    //----------------------------
    // Setup Singleton instance 
    private static GameController instance = null;
    
    private PlayerList           team;
    private boolean            action;
    private boolean        gameFinish;

    // Observers 
    public List<Observer> observers = new ArrayList<Observer>();
    
	//-----------------------------------
	// Get Instance of Singleton
	//-----------------------------------
	/**
	 * getInstance method returns single instance of WaterMeter.
	 * @return instance singleton WaterMeter object.
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
     * Constructor for PlayerTurn.
     */
    private GameController() {
        this.team         = PlayerList.getInstance();
        this.action       = false;
        this.gameFinish   = false;
    }    

    //----------------------------
	// Player actions
	//----------------------------
    /**
     * Try move player pawn.
     */
    public void tryMove(Player player, Scanner inputScanner){  
        System.out.println("\nPlayer "+player.getName()+" is on "+ playerPawnTileName(player));
        System.out.println("\nWhere would you like to move? :");
		boolean didMove = false;
        
        while (!didMove) {
            // Find Valid Tile on board
            SelectTile tileOnBoard = new SelectTile();
            tileOnBoard.findTile(inputScanner);
        
            // Try move only once
            didMove = true;
        
            // Move to new Island Tile
            if(player.move(tileOnBoard.getValidTiles().get(0))){
                System.out.println("\nPlayer "+player.getName()+" successfully moved to "
                                    +getName(tileOnBoard.getValidTiles().get(0)));
                action = true;
            }
        }
    }

    /**
     * Try Shore Up an Island Tile.
     */
    public void tryShoreUp(Player player, Scanner inputScanner){
        boolean isEngineer = false;
        boolean didShoreUp = false;
        List<IslandTile> shoredTiles = new ArrayList<IslandTile>();
        System.out.println("\nPlayer "+player.getName()+" is on "+ playerPawnTileName(player));
        
        if(!(player.getRole() instanceof Engineer)){
            System.out.println("\nWhich Island would you like to shore up? :");
            isEngineer = false;
        }else{
            System.out.println("\nWhich Two Islands would you like to shore up? :");
            isEngineer = true;
        }
        
        while (!didShoreUp) {
            // Find Valid Tile on board
            SelectTile tileOnBoard = new SelectTile();
            tileOnBoard.findTile(inputScanner);

            shoredTiles = tileOnBoard.getValidTiles();
            // Only attempt to ShoreUp once
            didShoreUp = true;

            // Shore Up Two Island Tile
            if(isEngineer && shoredTiles.size()==2){
                if(player.shoreUp(shoredTiles.get(0))){
                    System.out.println("\nPlayer "+player.getName()+" successfully Shored Up "
                    + getName(tileOnBoard.getValidTiles().get(0)));
                    action=true;
                }
                if(player.shoreUp(shoredTiles.get(1))){
                    System.out.println("\nPlayer "+player.getName()+" successfully Shored Up "
                    + getName(tileOnBoard.getValidTiles().get(1)));
                    action=true;
                }
            }else{
                // Shore Up single Tile
                if(player.shoreUp(shoredTiles.get(0))){
                    System.out.println("\nPlayer "+player.getName()+" successfully Shored Up "
                    + getName(tileOnBoard.getValidTiles().get(0)));
                    action=true;
                }
            }
        }
    }
    
    /**
     * Try Give Treasure Card to another Player.
     */
    public void tryGiveTreasureCard(Player player, Scanner inputScanner){ 
        System.out.println("\nPlayer "+player.getName()+" is on "+ playerPawnTileName(player));
        System.out.println("\nWhich Treasure card would you like to give? :");
        System.out.println(player.getHand().toString());

        // Find Valid Card in deck
        SelectCard cardInDeck = new SelectCard(player);
        cardInDeck.findCard(inputScanner);

        System.out.println("\nWhich player would you like to give a Treasure Card to? :");
		boolean didGiveCard = false;
        
        while (!didGiveCard) {
            // Find Valid Card in deck
            SelectPlayer playerInGame = new SelectPlayer();
            playerInGame.findPlayer(inputScanner);

            // Card given and Player to receive it
            Player teamMate = playerInGame.getValidPlayer().get(0);                    
            Card card =  cardInDeck.getValidCard(); 
            
            // Only attempt to give card once
            didGiveCard = true; 
            
            // Give Card
            if(player.giveTreasurerCard(card, teamMate)){
                System.out.println("\nPlayer "+player.getName()+" successfully gave "
                                    +card.getName().name()+" to "+teamMate.getName());       
                action = true;
            }
        }
    }

    /**
     * Try capture Treasure().
     */
    public void tryCaptureTreasure(Player player){
        // Try Capture treasure once
        if(player.captureTreasure()){
            System.out.println("\nPlayer "+player.getName()+" successfully captured "
                                +team.getLastTreasure().name());
            action = true;
        }
    }

    //----------------------------
	// Special actions
    //----------------------------
    /**
     * Try use a Special Action card within player Hand
     */
    public void tryUseSpecialCard(Player player, Scanner inputScanner){
        // Check if player has special cards
        if(!player.getHand().hasSpecialCard(SpecialCardEnums.SANDBAGS) && !player.getHand().hasSpecialCard(SpecialCardEnums.HELICOPTER_LIFT)){
            return;
        }

        // Check if player wants to use special card
        System.out.println("\nPlayer " + player.getName()+ " would you like to use a special card? (Yes or No)");
        System.out.println(player.getHand().getSpecialCards());

        String response = inputScanner.nextLine();
        if(response.toUpperCase().equals("NO")){
            return;
        }else {
            System.out.println("\nWhich card would you like to use? :");

            // Find Valid Card in deck
            SelectCard cardInDeck = new SelectCard(player);
            cardInDeck.findCard(inputScanner);

            if(cardInDeck.getValidCard().getName().equals(SpecialCardEnums.HELICOPTER_LIFT)){
                Board.getInstance().printBoard();
                tryUseHelicopterLiftCard(player,inputScanner);
            }else if(cardInDeck.getValidCard().getName().equals(SpecialCardEnums.SANDBAGS)){
                Board.getInstance().printBoard();
                tryUseSandbagsCard(player,inputScanner);
            }
        }
    }

     /**
     * Try use SandBags card.
     */
    public void tryUseSandbagsCard(Player player, Scanner inputScanner){
        System.out.println("\nPlayer "+player.getName()+" is on "+ playerPawnTileName(player));
        System.out.println("\nWhich Island would you like to shore up? :");
        boolean didShoreUp = false;
        
        while (!didShoreUp) {
            // Find Valid Tile on board
            SelectTile tileOnBoard = new SelectTile();
            tileOnBoard.findTile(inputScanner);

            // Only attempt to use Sandbags Card once
            didShoreUp =true;

            // Use Sandbags Card
            player.useSandbagsCard(tileOnBoard.getValidTiles().get(0));
        }
    }

    /**
     * Try use HelicopterLift card.
     */
    public void tryUseHelicopterLiftCard(Player player, Scanner inputScanner){
        
        // TODO: Needs to be verified
        // CheckWin Conditions
        setGameFinish(notifyAllObservers());
        if(gameFinish) return;

        System.out.println("\nPlayer "+player.getName()+" is on "+ playerPawnTileName(player));
        System.out.println("\nFlying Players? (Player names separated by space):");

        SelectPlayer flyingPlayers = new SelectPlayer();
        flyingPlayers.findPlayer(inputScanner);
 
        System.out.println("\nWhere would you like to fly? :");
		boolean didFly = false;
        
        while (!didFly) {
            // Find Valid Tile on board
            SelectTile tileOnBoard = new SelectTile();
            tileOnBoard.findTile(inputScanner);
            
            // Try fly only once
            didFly = true;
            
            // Fly to new Island Tile
            player.useHelicopterLiftCard(flyingPlayers.getValidPlayer(),tileOnBoard.getValidTiles().get(0));
        }
    }

    //----------------------------
	// Helper Methods
    //----------------------------
    /**
     * Method for player to escape sinking tile
     */
    public void tryEscapeSinkingTile(Player player, Scanner inputScanner){
        // Method to escape sinking tile
        System.out.println("\nPlayer " + player.getName()+ " is escaping a sinking tile!");
        Board.getInstance().printBoard();

        // Obtain new Tile Location
        System.out.println("\nWhere would you like to escape? :");
		boolean didEscape = false;
        
        while (!didEscape) {
            // Find Valid Tile on board
            SelectTile tileOnBoard = new SelectTile();
            tileOnBoard.findTile(inputScanner);
        
            // Try escape only once
            didEscape = true;
        
            if(player.getRole() instanceof Pilot){
                Pilot pilot = (Pilot) player.getRole();
                // Fly to new Island Tile
                if(pilot.fly(player.getPawn(),tileOnBoard.getValidTiles().get(0))){
                    System.out.println("\nPlayer "+player.getName()+" successfully flown to "
                                        +getName(tileOnBoard.getValidTiles().get(0)));
                }
            }else{
                 // Swim to new Island Tile
                if(player.swim(tileOnBoard.getValidTiles().get(0))){
                    System.out.println("\nPlayer "+player.getName()+" successfully swam to "
                                        +getName(tileOnBoard.getValidTiles().get(0)));
                }
            }
            // Notify LoseObserver, Check if player Drowned
            setGameFinish(notifyAllObservers());
        }
    }

    /**
	 * clean playerPawnTile function that returns the name of a players pawn Tile.
	 * @return Player pawn Tile name
	 */
	private String playerPawnTileName(Player player) {
        return getName(player.getPawn().getPawnTile());
    }
 
    /**
	 * clean getName function that returns the String name of an IslandTile.
	 * @param  IslandTile islandTile
     * @return String Name of IslandTile
	 */
	private String getName(IslandTile islandTile) {
		return islandTile.getTileName().name();
    }

    //----------------------------
	// Getter & Setter Methods
    //----------------------------
    /**
	 * Return boolean action.
     * @return boolean True or false
	 */
    public boolean validAction(){
        return this.action;
    }

    /**
	 * Set boolean action.
     * @param boolean bool
	 */
    public void setValidAction(boolean bool){
        this.action = bool;
    }

    /**
	 * Return game finish status.
     * @return boolean True or false
	 */
    public boolean getGameFinish(){
        return this.gameFinish;
    }

    /**
     * Set boolean gameFinish.
     * 
     * @param boolean bool
     * @return
     */
    public void setGameFinish(boolean bool) {
        this.gameFinish = bool;
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
    public void dettach(Observer ob){
		observers.remove(ob);
	}
	/**
	 * Notify all observers of subject change.
	 */
    public boolean notifyAllObservers(){
		for(Observer ob:observers){
			return ob.update();
		}
        return false;
    }
}

