package forbiddenIsland.gameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.board.Board;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.FloodDeck;
import forbiddenIsland.card.TreasureDeck;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.enums.TilesEnums;
import forbiddenIsland.gameplay.GameController;
import forbiddenIsland.gameplay.WaterMeter;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;


/**
 * GameView class manages all of the options a player can make whilst they have a turn.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class GameView {

    //----------------------------
	// Variables
	//----------------------------
    private FloodDeck       floodDeck;
    private Board               board;
    private WaterMeter     waterMeter;
    private TreasureDeck treasureDeck;
    private GameController          c; 

    //----------------------------
	// Constructor
	//----------------------------
    /**
     * Constructor for GameView.
     */
    public GameView() { 
        this.board        = Board.getInstance();
        this.waterMeter   = WaterMeter.getInstance();
        this.treasureDeck = TreasureDeck.getInstance();
        this.floodDeck    = FloodDeck.getInstance();

        this.c            = GameController.getInstance();
    }   

    //----------------------------
	// Public Methods
	//----------------------------
	/**
	 * Function that manages the players turn, giving Player all possible options.
	 */
    public void doTurn(Player thisPlayer, Scanner thisInputScanner) {
        Player player = thisPlayer;
        Scanner inputScanner = thisInputScanner;

        int     count=0;
        boolean turnOver = false;
        List<Card> drawnFloodCards = new ArrayList<Card>();
		
        System.out.println("It is "+player.getName()+"'s turn! Press [return] to begin.");
        @SuppressWarnings("unused")
        String playerStartsTurn = inputScanner.nextLine(); // Make player press return to confirm turn start
        
        seeBoard();
        printout("Adventurer: "+player.getRole().toString());

        //----------------------------
	    // Perform at most 3 actions
	    //----------------------------
		while (!turnOver && count<3 && !c.getGameFinish()) {

			giveOptions();
            int     userInput  = 0;
            boolean validInput = false;
            
			while (!validInput) {
				String userString = inputScanner.nextLine();
				
				try {userInput = Integer.parseInt(userString);} 
	            catch (NumberFormatException e) {continue;}
				
				if ((userInput >= 0) && (userInput <= 9)) {
					validInput = true;
				}
            }

			switch (userInput) {
			    case 0:  turnOver = true;                                               break;
                case 1:  c.tryMove(player,inputScanner);                                break;
			    case 2:  c.tryShoreUp(player,inputScanner);                             break;
			    case 3:  c.tryGiveTreasureCard(player,inputScanner);                    break;
			    case 4:  c.tryCaptureTreasure(player);                                  break;
                case 5:  c.tryUseSandbagsCard(player,inputScanner);                     break;
                case 6:  c.tryUseHelicopterLiftCard(player,inputScanner);               break;
                case 7:  seeBoard();                                                    break;
                case 8:  seeHand(player);                                               break;
                case 9:  seeTileNames();                                                break;
			    default: printout("CASE ERROR IN GameView.doTurn()");
            }
            count += (c.validAction())? 1: 0;

            c.setValidAction(false);
            printout("\nActions taken: "+count);
        }

        //----------------------------
	    // Draw 2 treasure Cards
        //----------------------------
        drawTreasureCard(player,inputScanner);
        drawTreasureCard(player,inputScanner);
        checkForDiscard(inputScanner);
            
        //----------------------------
	    // Draw # Flood cards equal to Water level
        //----------------------------
        switch(WaterMeter.getInstance().getWaterLevel()){
            case 1:
            case 2:  drawnFloodCards.addAll(floodDeck.drawCard(2)); break;
            case 3:
            case 4:
            case 5:  drawnFloodCards.addAll(floodDeck.drawCard(3)); break;
            case 6:
            case 7:  drawnFloodCards.addAll(floodDeck.drawCard(4)); break;
            case 8:
            case 9:  drawnFloodCards.addAll(floodDeck.drawCard(5)); break;
            default: printout("\nError: In GameView.doTurn() Draw Flood Cards");
        }
        
        //----------------------------
	    // Flip Island Tiles
        //----------------------------
        flipIslandTiles(drawnFloodCards,inputScanner);

        // End turn
        printout("\nYour turn has ended.\n");
	}
    
    //----------------------------
	// Private Methods
	//----------------------------
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
        printout("[9]    Show Tile Names");
		printout("[0]    end  turn");
    }

    /**
	 * Printout the String representation of each Tile.
	 */
	private void seeTileNames() {
		List<String> tileNames = new ArrayList<String>();
		for(TilesEnums tile:TilesEnums.values()){
			 tileNames.add("\n["+tile.getMapString()+"]"+" : "+tile.toString());
		}
		printout(String.join(" ", tileNames));
    }

    //----------------------------
	// Private Helper Methods
    //----------------------------
    /**
     * Change the state of the Island Tiles matching the
     * the drawn flood cards. From DRY to Flooded, or from 
     * Flooded to SUNK. 
     */
    private void flipIslandTiles(List<Card> floodcards,Scanner inputScanner){
        for(Card c:floodcards){
            TilesEnums tileName = (TilesEnums) c.getName();
            board.getIslandTile(tileName).flip();
        }
        // Check if there are sinking players and try escape
        for(Player p:PlayerList.getInstance().getAllPlayers()){
            if(p.getPawn().getPawnTile().isSunk()){
                c.tryEscapeSinkingTile(p,inputScanner);
            }
        }
        // Notify LoseObserver, Check if Fools Landing or Treasure Tiles Sunk
        c.setGameFinish(c.notifyAllObservers());
    }

    /**
     * Draw a treasrue card from treasure deck.
     */
    private void drawTreasureCard(Player player, Scanner inputScanner){
        Card drawnCard = treasureDeck.drawCard();

        if(drawnCard.getName().equals(SpecialCardEnums.WATERS_RISE)){
            waterMeter.raiseWaterLevel();
            treasureDeck.discard(drawnCard);
            // Notify LoseObserver, Check if waterLevel at Skull&Bones
            c.setGameFinish(c.notifyAllObservers());
        }
        else{
            player.getHand().addCard(drawnCard);
        }
        
        // Check if need to discard or use a card
        if(playerDeck(player).size()>5)
            c.tryUseSpecialCard(player,inputScanner);
        if(playerDeck(player).size()>5)
            discardChosenCard(player,inputScanner);
    }

    /**
     * Discard extra card in player deck.
     */
    private void discardChosenCard(Player player, Scanner inputScanner){
        printout("\nWhich card would you like to discard? :");
        seeHand(player);

        // Find Valid Card in deck
        SelectCard cardInDeck = new SelectCard(player);
        cardInDeck.findCard(inputScanner);
        
        // Discard card
        treasureDeck.discard(cardInDeck.getValidCard());
        playerDeck(player).remove(cardInDeck.getValidCardIndex());
    }

    /**
     * Check if any team member needs to discard or use a Special Card 
     */
    private void checkForDiscard(Scanner inputScanner){
        for(Player p:PlayerList.getInstance().getAllPlayers()){
            c.tryUseSpecialCard(p,inputScanner);
            if(playerDeck(p).size()>5)
                discardChosenCard(p,inputScanner);
        }
    }

    //----------------------------
	// Clean Methods
    //----------------------------
    /**
	 * clean seeBoard function that prints the player Board on screen.
	 */
    private void seeBoard(){
        // Check if board is visible
        board.printBoard();
    }
    /**
	 * clean seeHand function that prints the player Hand on screen.
	 */
    private void seeHand(Player player){
        // Check if hand is visible
        printout(player.getHand().toString());
    }

    /**
	 * clean playerDeck function that returns the Hand Deck of a player.
	 * @return Player Hand Deck
	 */
	private List<Card> playerDeck(Player player) {
		return player.getHand().getDeck();
    }

    /**
	 * clean printout function to print to the console.
	 * @param toPrint The string to be printed.
	 */
	private void printout(String toPrint) {
		System.out.println(toPrint);
    }
}
