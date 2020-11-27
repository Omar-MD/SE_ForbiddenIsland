package forbiddenIsland.gameplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.FloodDeck;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * PlayerTurn class manages all of the options a player can make whilst 
 * they have a turn.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class PlayerTurn {
    
    //----------------------------
	// Variables
    //----------------------------
    private Scanner      inputScanner;
    private Board        board;
    private WaterMeter   waterMeter;
    private TreasureDeck treasureDeck;
    private FloodDeck    floodDeck;
    private PlayerList   team;
    private Player       player;
    private int          lastAction;
    private boolean      moved;
    private boolean      shoredUp;
    private boolean      gaveCard;
    private boolean      capturedT;

    //----------------------------
	// Constructor
	//----------------------------
   /**
     * Constructor for PlayerTurn.
     * @param thisPlayer The player whose turn it is.
     * @param inputScanner The one console input scanner.
     */
    public PlayerTurn(Player thisPlayer, Scanner inputScanner) {
        this.inputScanner = inputScanner;
        this.board        = Board.getInstance();
        this.waterMeter   = WaterMeter.getInstance();
        this.treasureDeck = TreasureDeck.getInstance();
        this.floodDeck    = FloodDeck.getInstance();
        this.team         = PlayerList.getInstance();
        this.player       = thisPlayer;
        this.lastAction   = 0;
        this.moved        = false;
        this.shoredUp     = false;
        this.gaveCard     = false;
        this.capturedT    = false; 
    }    

    //----------------------------
	// Public Methods
	//----------------------------
	/**
	 * Function that manages the players turn, giving them the 6
	 * possible options.
	 */
    public void doTurn() {
		
        int     count=0;
        boolean turnOver = false;
        List<Card> drawnFloodCards = new ArrayList<Card>();
		
        System.out.println("It is "+player.getName()+"'s turn! Press [return] to begin.");
        @SuppressWarnings("unused")
        String playerStartsTurn = inputScanner.nextLine(); // Make player press return to confirm turn start
        

        //----------------------------
	    // Perform at most 3 actions
	    //----------------------------
		while (!turnOver && count<=3) {

			giveOptions();
            int     userInput  = 0;
            boolean validInput = false;;
            
			while (!validInput) {
				String userString = inputScanner.nextLine();
				
				try {userInput = Integer.parseInt(userString);} 
	            catch (NumberFormatException e) {continue;}
				
				if ((userInput >= 0) && (userInput <= 8)) {
					validInput = true;
				}
			}
			switch (userInput) {
			    case 0:  turnOver = true;                          lastAction=0;             break;
                case 1:  tryMove();                 if(moved)    { lastAction=1; count+=1;}  break;
			    case 2:  tryShoreUp();              if(shoredUp) { lastAction=2; count+=1;}  break;
			    case 3:  tryGiveTreasureCard();     if(gaveCard) { lastAction=3; count+=1;}  break;
			    case 4:  tryCaptureTreasure();      if(capturedT){ lastAction=4; count+=1;}  break;
                case 5:  tryUseSandbagsCard();                     lastAction=5;             break;
                case 6:  tryUseHelicopterLiftCard();               lastAction=6;             break;
                case 7:  board.printBoard();                       lastAction=7;             break;
                case 8:  printout(player.getHand().toString());        lastAction=8;             break;
			    default: printout("CASE ERROR IN PlayerTurn.doTurn()");
			}
			printout("\nActions taken: "+count);
        }

        //----------------------------
	    // Draw 2 treasure Cards
        //----------------------------
        drawTreasureCard();
        drawTreasureCard();

        //----------------------------
	    // Draw # Flood cards equal to Water level
        //----------------------------
        switch(waterMeter.getWaterLevel()){
            case 1:
            case 2:  drawnFloodCards.addAll(floodDeck.drawCard(2)); break;
            case 3:
            case 4:
            case 5:  drawnFloodCards.addAll(floodDeck.drawCard(3)); break;
            case 6:
            case 7:  drawnFloodCards.addAll(floodDeck.drawCard(4)); break;
            case 8:
            case 9:  drawnFloodCards.addAll(floodDeck.drawCard(5)); break;
            default: printout("\nError: In PlayerTurn.doTurn() Draw Flood Cards");
        }
        
        //----------------------------
	    // Flip 
        //----------------------------
        flipIslandTiles(drawnFloodCards);

        // End turn
        printout("\nYour turn has ended.\n");
        this.moved        = false;
        this.shoredUp     = false;
        this.gaveCard     = false;
        this.capturedT    = false; 
        count=0;
	}
	
    /**
	 * Printout the options a player can make for their turn.
	 */
	private void giveOptions() {
		printout("\nWhat do you want to do?\nYou're options are:");
		printout("[1]    Move");
		printout("[2]    Shore Up ");
		printout("[3]    Give a Treasure Card");
        printout("[4]    Capture a Treasure");
        printout("[5]    Use a SandBags Card");
        printout("[6]    Use a HelicopterLift Card");
        printout("[7]    Show board");
        printout("[8]    Show Hand");
		printout("[0]    end  turn");
    }

    //----------------------------
	// Private Methods
    //----------------------------
    
    /**
     * Change the state of the Island Tiles matching the
     * the drawn flood cards. From DRY to Flooded, or from 
     * Flooded to SUNK. 
     */
    private void flipIslandTiles(List<Card> floodcards){
        for(Card c:floodcards){
            TilesEnums tileName = (TilesEnums) c.getName();
            board.getIslandTile(tileName).flip();
        }
        //TODO: Method to check if failed to move from sinking tile
    }

    /**
     * Draw a treasrue card from treasure deck.
     */
    private void drawTreasureCard(){
        Card drawnCard = treasureDeck.drawCard();
        if(drawnCard instanceof SpecialCard && drawnCard.getName().equals(SpecialCardEnums.WATERS_RISE))
            waterMeter.raiseWaterLevel();
        else
            player.getHand().addCard(treasureDeck.drawCard());
        discardChosenCard();
    }

    /**
     * Discard extra card in player deck.
     */
    private void discardChosenCard(){
        // Check if need to discard a card
        if(playerDeck().size()<=5)
            return;
        
        printout("\nWhich card would you like to discard? :");
        seeHand();

        // Find Valid Card in deck
        CardInDeck cardInDeck = new CardInDeck(player);
        cardInDeck.findCard(inputScanner);
        
        // Discard card
        treasureDeck.discard(cardInDeck.getValidCard());
        playerDeck().remove(cardInDeck.getValidCardIndex());
    }

    /**
     * Try move player pawn.
     */
    private void tryMove(){   
        printout("\nPlayer "+player.getName()+" is on "+ playerPawnTileName());
        printout("\nWhere would you like to move? :");
        seeBoard();
		boolean didMove = false;
        
        while (!didMove) {
            // Find Valid Tile on board
            TileOnBoard tileOnBoard = new TileOnBoard(inputScanner);
        
            // Try move only once
            didMove = true;
        
            // Move to new Island Tile
            if(player.move(tileOnBoard.getValidTile())){
                printout("\nPlayer "+player.getName()+"successfully moved to "
                                    +getName(tileOnBoard.getValidTile()));
                moved = true;
               //Show updated board
                board.printBoard();
            }
        }
    }

    /**
     * Try Shore Up an Island Tile.
     */
    private void tryShoreUp(){
        printout("\nPlayer "+player.getName()+" is on "+ playerPawnTileName());
        printout("\nWhich Island would you like to shore up? :");
        seeBoard();
        boolean didShoreUp = false;
        
        while (!didShoreUp) {
            // Find Valid Tile on board
            TileOnBoard tileOnBoard = new TileOnBoard(inputScanner);

            // Only attempt to ShoreUp once
            didShoreUp = true;
            
            // Shore Up Island Tile
            if(player.shoreUp(tileOnBoard.getValidTile())){
                printout("\nPlayer "+player.getName()+"successfully Shored Up " 
                                    + getName(tileOnBoard.getValidTile()));
                shoredUp = true;
                //Show updated board
                board.printBoard();
            }
        }
    }
    
    /**
     * Try Give Treasure Card to another Player.
     */
    private void tryGiveTreasureCard(){ 
        printout("\nPlayer "+player.getName()+" is on "+ playerPawnTileName());
        printout("\nWhich Treasure card would you like to give? :");
        seeHand();

        // Find Valid Card in deck
        CardInDeck cardInDeck = new CardInDeck(player);
        cardInDeck.findCard(inputScanner);

        printout("\nWhich player would you like to give a Treasure Card to? :");
		boolean didGiveCard = false;
        
        while (!didGiveCard) {
            // Find Valid Card in deck
            PlayerInGame playerInGame = new PlayerInGame();
            playerInGame.findPlayer(inputScanner);

            // Card given and Player to receive it
            Player teamMate = playerInGame.getValidPlayer();                    
            Card card =  cardInDeck.getValidCard(); 
            
            // Only attempt to give card once
            didGiveCard = true; 
            
            // Give Card
            if(player.giveTreasurerCard(card, teamMate)){
                printout("\nPlayer "+player.getName()+" successfully gave "
                                    +card.getName().name()+" to "+teamMate.getName());       
                gaveCard = true;
                //Show updated Hand
                player.getHand().toString();
            }
        }
    }

    /**
     * Try capture Treasure().
     */
    private void tryCaptureTreasure(){
        seeBoard();

        // Try Capture treasure once
        if(player.captureTreasure()){
            printout("\nPlayer "+player.getName()+" successfully captured "
                                +team.getLastTreasure().name());
            capturedT = true;
            //Show updated Hand and board
            board.printBoard();
            player.getHand().toString();
        }
    }

     /**
     * Try use SandBags card.
     */
    private void tryUseSandbagsCard(){
        printout("\nPlayer "+player.getName()+" is on "+ playerPawnTileName());
        printout("\nWhich Island would you like to shore up? :");
        seeBoard();
        boolean didShoreUp = false;
        
        while (!didShoreUp) {
            // Find Valid Tile on board
            TileOnBoard tileOnBoard = new TileOnBoard(inputScanner);

            // Only attempt to use Sandbags Card once
            didShoreUp =true;
            
            printout(tileOnBoard.getValidTile().toString());

            // Use Sandbags Card
            if(player.useSandbagsCard(tileOnBoard.getValidTile())){
                //Show updated board
                board.printBoard();
            }
        }
    }

    /**
     * Try use HelicopterLift card.
     */
    private void tryUseHelicopterLiftCard(){
        printout("\nPlayer "+player.getName()+" is on "+ playerPawnTileName());
        printout("\nWhich Players are flying with you? (Player names separated by commas):");
        seeBoard();
        List<Player> flyingPlayers = new ArrayList<Player>();
        boolean validPlayers = false; 

        // Obtain List of flying Players
        while(!validPlayers){
            String userString = inputScanner.nextLine();
            List<String> names = new ArrayList<String>(Arrays.asList(userString.split(",")));

            // Find referenced players
            for(int j=0; j< names.size(); j++){
                for(Player teamMate: team.getAllPlayers()){
                    if(teamMate.getName().equals(names.get(j))){ 
                        flyingPlayers.add(team.getPlayer(team.getPlayerIndex(teamMate)));
                        names.remove(0);
                    }
                    if(names.isEmpty() && !flyingPlayers.isEmpty())
                        validPlayers = true;
                }
            }
            // Check if valid players
            if(validPlayers){ printout("\nValid Player list");}
            else { printout("\nIncorrect Player list"); continue; }
        }
 
        printout("\nWhere would you like to fly? :");
		boolean didFly = false;
        
        while (!didFly) {
            // Find Valid Tile on board
            TileOnBoard tileOnBoard = new TileOnBoard(inputScanner);
            
            // Try fly only once
            didFly = true;
            
            // Fly to new Island Tile
            if(player.useHelicopterLiftCard(flyingPlayers,tileOnBoard.getValidTile())){
               //Show updated board
                board.printBoard();
                //notify
            }
        }
    }

    //----------------------------
	// Helper Methods
    //----------------------------
    /**
	 * clean seeBoard function that prints the player Board on screen.
	 */
    private void seeBoard(){
        // Check if board is visible
        if(this.lastAction!=7)
            board.printBoard();
    }
    /**
	 * clean seeHand function that prints the player Hand on screen.
	 */
    private void seeHand(){
        // Check if hand is visible
        if(lastAction!=8)
            System.out.println(player.getHand().toString());
    }
	/**
	 * clean printout function to print to the console.
	 * @param toPrint The string to be printed.
	 */
	private void printout(String toPrint) {
		System.out.println(toPrint);
    }
    /**
	 * clean playerPawnTile function that returns the name of a players pawn Tile.
	 * @return Player pawn Tile name
	 */
	private String playerPawnTileName() {
        return getName(player.getPawn().getPawnTile());
    }
    /**
	 * clean playerDeck function that returns the Hand Deck of a player.
	 * @return Player Hand Deck
	 */
	private List<Card> playerDeck() {
		return player.getHand().getDeck();
    }
    /**
	 * clean getName function that returns the String name of an IslandTile.
	 * @param  IslandTile islandTile
     * @return String Name of IslandTile
	 */
	private String getName(IslandTile islandTile) {
		return islandTile.getTileName().name();
	}
}