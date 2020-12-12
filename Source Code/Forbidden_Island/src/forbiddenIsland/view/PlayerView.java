package forbiddenIsland.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.adventurer.Engineer;
import forbiddenIsland.adventurer.Pilot;
import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.gameplay.GameController;
import forbiddenIsland.gameplay.Treasure;
import forbiddenIsland.player.Player;
import forbiddenIsland.player.PlayerList;

/**
 * PlayerView displays Player info and passes Player related user input to Game Controller.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class PlayerView {
    //------------------------
	// Variables
	//------------------------
	private GameView       gameView;
    private GameController controller;
    private Scanner        inputScanner;
    private boolean        action;
    private boolean        isValidPlayer;
    private Player         validPlayer;
    private int            validPlayerNum;
    private Player         thisPlayer;
    private int            thisPlayerNum;
    private PlayerList     team;
    
    //------------------------
	// Constructor
	//------------------------
    public PlayerView(Player thisPlayer){
    	this.gameView       = GameView.getInstance();
    	this.inputScanner   = gameView.getScanner();
    	this.controller     = gameView.getController();
    	this.thisPlayer         = thisPlayer;
    	this.thisPlayerNum  = (int) thisPlayer.getChar() - 48;
        this.isValidPlayer  = false;
        this.validPlayerNum = 0;
        this.team           = PlayerList.getInstance();
    }

    /**
	 * Function that manages the players turn, giving Player all possible options.
	 */
    public void doTurn() {

        int count = 0;
        boolean turnOver = false;

        printout("It is "+thisPlayer.getName()+"'s (Player "+ thisPlayer.getChar() +") turn! Press [return] to begin.");
        inputScanner.nextLine(); // Make player press return to confirm turn start
        
        controller.showBoard();
        printout("Adventurer: "+thisPlayer.getRole().toString());

        //----------------------------
	    // Perform at most 3 actions
	    //----------------------------
		while (!turnOver && count<3 && !controller.getGameFinish()) {

            int     userInput  = 0;
            boolean validInput = false;

			while (!validInput) {
				gameView.giveOptions();
				String userString = inputScanner.nextLine();

				try {userInput = Integer.parseInt(userString);} 
	            catch (NumberFormatException e) {
	            	printout("\n" + userString + " is not a valid input!");
	            	continue;
	            }

				if ((userInput >= 0) && (userInput <= 9)) {
					validInput = true;
				} else {
					printout("\n" + userInput + " is not a valid input!");
				}
            }

			switch (userInput) {
			    case 0:  turnOver = true;                              break;
                case 1:  tryMove();                                    break;
			    case 2:  tryShoreUp();                                 break;
			    case 3:  tryGiveTreasureCard();                        break;
			    case 4:  tryCaptureTreasure();                         break;
                case 5:  tryUseSpecialCard(thisPlayer);                break;
                case 6:  controller.showBoard();                       break;
                case 7:  seeHand(thisPlayer);                          break;
                case 8:  gameView.seeHelp();                           break;
                case 9:  tryTeamPlay();                                break;
			    default: printout("CASE ERROR IN GameView.doTurn()");
            }

			// Increment count if action is valid
			count += (isActionValid())? 1: 0;
            setValidAction(false);
            printout("\nActions taken: "+count);
        }

        //----------------------------
	    // Draw 2 treasure Cards
        //----------------------------
		controller.drawTreasureCard(thisPlayer);
		// Check if need to discard or use a card
        checkForDiscard(thisPlayer);
		controller.drawTreasureCard(thisPlayer);
		// Check if need to discard or use a card
        checkForDiscard(thisPlayer);
            
        //----------------------------
	    // Draw # Flood cards equal to Water level
        //----------------------------
        List<Card> drawnFloodCards = controller.drawFloodCards();
        
        //----------------------------
	    // Flip Island Tiles
        //----------------------------
        controller.flipIslandTiles(drawnFloodCards);
        // Check if there are sinking players and try escape

        // End turn
        printout("\nYour turn has ended.\n");
	}

    /**
     * Discard extra card in player deck. If special card is chosen, it can be used first.
     */
    private void checkForDiscard(Player player){
    	if(getPlayerDeck(player).size()>5) {
    		printout("\n" + player.getName()+" (Player "+ thisPlayer.getChar() + ") has more than 5 cards in hand.");
    		printout("\nWhich card would you like to discard? :");
    		printout("\nNOTE: If you choose to discard a special card, you may use the card first.");
    		seeHand(player);

    		// Find Valid Card in deck
    		Card card = new CardView(player).findCard();

    		if (card instanceof SpecialCard) {
    			boolean validInput = false;
    			while (!validInput) {
    				System.out.println("\nWould you like to use this special card? : (Yes or No)");
    				String response = inputScanner.nextLine();
    				if (response.toUpperCase().equals("YES")) {
    					validInput = true;
    					if(card.getName().equals(SpecialCardEnums.HELICOPTER_LIFT)) {
    	        			controller.showBoard();
    	        			tryUseHelicopterLiftCard(player);
    	        		} else if(card.getName().equals(SpecialCardEnums.SANDBAGS)) {
    	        			controller.showBoard();
    	        			tryUseSandbagsCard(player);
    	        		}
    				} else if (response.toUpperCase().equals("NO")) {
    					validInput = true;
    				} else {
    					System.out.println("\nInvalid Input. Please enter 'Yes' or 'No'");
    				}
    			}
    		}
    		controller.discardChosenCard(player, card);
    	}
    }

    /**
     * Method to display and try perform team play options selected.
     */
    public void tryTeamPlay(){
    	int     userInput  = 0;
    	boolean returnCall = false;
    	boolean validInput = false;

    	while (!returnCall) {
    		gameView.giveTeamPlayOptions();
    		while (!validInput) {
    			String userString = inputScanner.nextLine();

    			try {userInput = Integer.parseInt(userString);} 
    			catch (NumberFormatException e) {
    				printout("\n" + userString + " is not a valid input!");
    				continue;
    			}

    			if ((userInput >= 0) && (userInput <= 3)) {
    				validInput = true;
    			} else {
    				printout("\n" + userInput + " is not a valid input!");
    			}
    		}

    		validInput = false;

    		if (userInput == 0) {
    			returnCall = true;
    		} else if (userInput == 1) {
    			System.out.println("\nWhich player hand would you like to see? :");
    	    	System.out.println(team.printOtherPlayers(thisPlayer));

    	    	// Get a player from input and view the hand
    	    	seeHand(findOtherPlayer());
    		} else if (userInput == 2) {
    			System.out.println("\nWhich player would like to use a special card? :");
    	    	System.out.println(team.printOtherPlayers(thisPlayer));

    	    	// Get a player from input and try use a special card
    	    	tryUseSpecialCard(findOtherPlayer());
    		} else {
    			printout("The treasures captured so far are:");
    			for (Treasure treasure: team.getCapturedTreasure()) {
    				printout(treasure.getTreasureName().toString());
    			}
    		}
        }
    }

    //----------------------------
    // Player actions
    //----------------------------
    /**
     * Try move player pawn.
     */
    public void tryMove(){
    	System.out.println("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is on "+ playerPawnTileName());
    	System.out.println("\nWhere would you like to move? (For Example: SIG)");
    	boolean didMove = false;

    	while (!didMove) {
    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();

    		// Try move only once
    		didMove = true;

    		// Move to new Island Tile
    		if(controller.requestMove(thisPlayer, validTile)){
    			System.out.println("\nPlayer "+thisPlayer.getName()+" successfully moved to "
    					+getName(validTile));
    			action = true;
    		}
    	}
    }

    /**
     * Try Shore Up an Island Tile.
     */
    public void tryShoreUp(){
    	boolean validInput = false;
    	boolean didShoreUp = false;
    	List<IslandTile> shoredTiles = new ArrayList<IslandTile>();
    	System.out.println("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is on "+ playerPawnTileName());

    	System.out.println("\nWhich Island would you like to shore up? :");

    	while (!didShoreUp) {
    		// Find Valid Tile on board and add to list
    		shoredTiles.add(new TileView().findTile());

    		if(thisPlayer.getRole() instanceof Engineer){
    			while (!validInput) {
    				System.out.println("\nWould you like to shore up another tile? : (Yes or No)");
    				String response = inputScanner.nextLine();
    				switch (response.toUpperCase()) {
    				case "YES":
    					validInput = true;
    					System.out.println("\nWhich Island would you like to shore up? :");
    					shoredTiles.add(new TileView().findTile());
    					break;
    				case "NO":
    					validInput = true;
    					break;
    				default: 
    					System.out.println("\nInvalid Input. Please enter 'Yes' or 'No'");
    				}
    			}
    		}

    		// Only attempt to ShoreUp once
    		didShoreUp = true;

    		// Shore Up Two Island Tile
    		if(shoredTiles.size() == 2) {
    			if(controller.requestShoreUp(thisPlayer, shoredTiles.get(0))){
    				System.out.println("\nPlayer "+thisPlayer.getName()+" successfully Shored Up "
    						+ getName(shoredTiles.get(0)));
    				action=true;
    			}
    			if(controller.requestShoreUp(thisPlayer, shoredTiles.get(1))) {
    				System.out.println("\nPlayer "+thisPlayer.getName()+" successfully Shored Up "
    						+ getName(shoredTiles.get(1)));
    				action=true;
    			}
    		} else {
    			// Shore Up single Tile
    			if(controller.requestShoreUp(thisPlayer, shoredTiles.get(0))) {
    				System.out.println("\nPlayer "+thisPlayer.getName()+" successfully Shored Up "
    						+ getName(shoredTiles.get(0)));
    				action=true;
    			}
    		}
    	}
    }
      
    /**
     * Try Give Treasure Card to another Player.
     */
    public void tryGiveTreasureCard(){ 
    	System.out.println("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is on "+ playerPawnTileName());
    	System.out.println("\nWhich Treasure card would you like to give? :");
    	List<Card> tCards = thisPlayer.getHand().getTreasureCards();
    	System.out.println(thisPlayer.getHand().printCards(tCards));

    	// Find Valid Card in deck
    	Card card = new CardView(thisPlayer).findTreasureCard();

    	System.out.println("\nWhich player would you like to give a Treasure Card to? :");
    	System.out.println(team.printOtherPlayers(thisPlayer));
    	boolean didGiveCard = false;

    	while (!didGiveCard) {
    		// Find the selected teamMate
    		Player teamMate = findOtherPlayer();

    		printout("Before giving the card");
    		seeHand(thisPlayer);
    		seeHand(teamMate);
    		// Only attempt to give card once
    		didGiveCard = true; 

    		// Give Card
    		if(controller.requestGiveTreasureCard(thisPlayer, card, teamMate)){
    			printout("After giving the card");
    			seeHand(thisPlayer);
    			seeHand(teamMate);
    			checkForDiscard(teamMate);
    			System.out.println("\nPlayer "+thisPlayer.getName()+" successfully gave "
    					+card.toString()+" to "+teamMate.getName());       
    			action = true;
    		}
    	}
    }

    /**
     * Try capture Treasure().
     */
    public void tryCaptureTreasure(){
    	// Try Capture treasure once
    	if(controller.requestCaptureTreasure(thisPlayer)){
    		System.out.println("\nPlayer "+thisPlayer.getName()+" successfully captured "
    				+team.getLastTreasure().toString());
    		action = true;
    	}
    }

    //----------------------------
    // Special actions
    //----------------------------
    /**
     * Try use Special card.
     */
    public void tryUseSpecialCard(Player player){
    	if(!player.getHand().hasSpecialCard(SpecialCardEnums.SANDBAGS) 
    			&& !player.getHand().hasSpecialCard(SpecialCardEnums.HELICOPTER_LIFT)){
    		printout("Player has no special card");
            return;
        }
    	System.out.println("\nWhich special card would you like to use? :");
    	List<Card> sCards = player.getHand().getSpecialCards();
    	System.out.println(player.getHand().printCards(sCards));

    	// Find Valid Card in deck
    	Card card = new CardView(player).findSpecialCard();

    	if(card.getName().equals(SpecialCardEnums.HELICOPTER_LIFT)) {
    		controller.showBoard();
    		tryUseHelicopterLiftCard(player);
    	} else if(card.getName().equals(SpecialCardEnums.SANDBAGS)) {
    		controller.showBoard();
    		tryUseSandbagsCard(player);
    	}
    }

    /**
     * Try use SandBags card.
     */
    public void tryUseSandbagsCard(Player player){
    	System.out.println("\n"+player.getName()+" (Player "+ player.getChar() +") is on "+ playerPawnTileName());
    	System.out.println("\nWhich Island would you like to shore up? :");
    	boolean didShoreUp = false;

    	while (!didShoreUp) {
    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();

    		// Use Sandbags Card
    		if (controller.requestUseSandbagsCard(player, validTile)) {
    			didShoreUp = true;
    		} else {
    			printout("\nPlease enter a valid tile:");
    		}
    	}
    }

    /**
     * Try use HelicopterLift card.
     */
    public void tryUseHelicopterLiftCard(Player player){

    	// TODO: Needs to be verified
    	// CheckWin Conditions
    	controller.notifyAllObservers();
    	//setGameFinish(notifyAllObservers());
    	if(controller.getGameFinish()) return;

    	boolean didFly = false;

    	while (!didFly) {
    		System.out.println("\n"+player.getName()+" (Player "+ player.getChar() +") is on "+ playerPawnTileName());
        	System.out.println("\nWhich players would you like to lift off?: (Please input one player character at a time)");

        	List<Player> flyingPlayers = findPlayers();
        	System.out.println("\nWhere would you like to fly? :");

    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();

    		// Fly to new Island Tile
    		if (controller.requestUseHelicopterLiftCard(player, flyingPlayers,validTile)) {
    			didFly = true;
    		}
    	}
    }

    //----------------------------
    // Helper Methods
    //----------------------------
    /**
     * Method for player to escape sinking tile
     */
    public void tryEscapeSinkingTile(){
    	// Method to escape sinking tile
    	System.out.println("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is escaping a sinking tile!");
    	Board.getInstance().printBoard();

    	// Obtain new Tile Location
    	System.out.println("\nWhere would you like to escape? :");
    	boolean didEscape = false;

    	while (!didEscape) {
    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();

    		// Try escape only once
    		didEscape = true;

    		if(thisPlayer.getRole() instanceof Pilot) {
    			if(controller.requestEscapeSinkingTileByFlight(thisPlayer, validTile)) {
    				System.out.println("\nPlayer "+thisPlayer.getName()+" has successfully flown to "
    						+getName(validTile));
    			}
    		} else {
    			// Swim to new Island Tile
    			if(controller.requestEscapeSinkingTileBySwim(thisPlayer, validTile)) {
    				System.out.println("\nPlayer "+thisPlayer.getName()+" successfully swam to "
    						+getName(validTile));
    			}
    		}
    		// Notify LoseObserver, Check if player Drowned
    		controller.notifyAllObservers();
    		//setGameFinish(notifyAllObservers());
    	}
    }

    /**
  	 * Find selected other player.
  	 * @param Scanner User input
  	 * @return Selected player
  	 */
  	public Player findOtherPlayer(){
  		// Obtain a valid player
  		while(!isValidPlayer){
  			validPlayerNum = findValidPlayerNum();
  			// If validPlayerNum remains 0, it means user input was invalid
  			if (validPlayerNum == 0) {
  				continue;
  			}
  			// Player cannot choose him/her self and must be within the PlayerList range
  			else if((validPlayerNum >= 1) && (validPlayerNum != thisPlayerNum) && 
  					(validPlayerNum <= team.getAllPlayers().size())){
  				System.out.println("\nValid player character chosen");
  				validPlayer = team.getPlayer(validPlayerNum-1);
  				isValidPlayer = true;
  			}
  			else {
  				System.out.println("\nIncorrect player character chosen.");
  				System.out.println("\nPlease choose a player character from the above options: (Eg: 2)");
  			}
  		}
  		isValidPlayer = false;
  		validPlayerNum = 0;
  		return validPlayer;
  	}

  	/**
  	 * Find selected players.
  	 * @param Scanner User input
  	 * @return Selected List of players
  	 */
  	public List<Player> findPlayers(){
  		List<Player> selectedPlayers = new ArrayList<Player>();
  		List<Player> remainingPlayers = new ArrayList<Player>();
  		List<Integer> remainingPlayerNums = new ArrayList<Integer>();
  		boolean userCall = false;

  		for(Player p: team.getAllPlayers()){
  			remainingPlayers.add(p);
  			remainingPlayerNums.add(team.getPlayerIndex(p)+1);
  		}
  		// While user hasn't requested return, continue asking for more players
  		while(!userCall){

  			for(Player p: remainingPlayers){
  				System.out.println(p.toString());
  			}
  			System.out.println("[" + (findMax(remainingPlayerNums)+1) +"] : End choosing players.");

  			while(!userCall && !isValidPlayer){
  				validPlayerNum = findValidPlayerNum();
  				// If validPlayerNum remains 0, it means user input was invalid
  				if (validPlayerNum == 0) {
  					continue;
  				}
  				// Player cannot choose him/her self and must be within the PlayerList range
  				else if(remainingPlayerNums.contains(validPlayerNum)) {
  					System.out.println("\nValid player character chosen");
  					validPlayer = remainingPlayers.remove(remainingPlayerNums.indexOf(validPlayerNum));
  					selectedPlayers.add(validPlayer);
  					remainingPlayerNums.remove(Integer.valueOf(validPlayerNum));
  					isValidPlayer = true;
  				}
  				else if(validPlayerNum == (findMax(remainingPlayerNums)+1)) {
  					System.out.println("\nEnd choosing players and return");
  					userCall = true;
  				}
  				else {
  					System.out.println("\nIncorrect player character chosen.");
  					System.out.println("\nPlease choose a player character from the above options: (Eg: 2)");
  				}
  			}
  			isValidPlayer = false;
  			validPlayerNum = 0;
  		}
  		return selectedPlayers;
  	}

  	/**
  	 * Returns chosen player number.
  	 * @return Integer player number 
  	 */
  	public int findValidPlayerNum(){
  		String userString = inputScanner.nextLine();	
  		try {validPlayerNum = Integer.parseInt(userString);} 
  		catch (NumberFormatException e) {
  			System.out.println("\n" + userString + " is not a valid input!");
  		}
  		return validPlayerNum;
  	}

  	/**
  	 * Returns the maximum of an Integer list or 0 if list is empty
  	 * @return Integer maximum 
  	 */
  	public int findMax(List<Integer> list){
  		if (list.isEmpty())
  			return 0;
  		return Collections.max(list);
  	}

    /**
     * clean playerPawnTile function that returns the name of a players pawn Tile.
     * @return Player pawn Tile name
     */
  	private String playerPawnTileName() {
          return getName(thisPlayer.getPawn().getPawnTile());
      }
   
  	/**
  	 * clean getName function that returns the String name of an IslandTile.
  	 * @param  IslandTile islandTile
  	 * @return String Name of IslandTile
  	 */
  	private String getName(IslandTile islandTile) {
  		return islandTile.getTileName().toString();
  	}

  	//----------------------------
  	// Getter & Setter Methods
  	//----------------------------
  	/**
  	 * Return boolean action.
  	 * @return boolean True or false
  	 */
  	public boolean isActionValid(){
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
  	 * Returns if chosen players are valid.
  	 * @return Boolean True or false
  	 */
  	public boolean isValidPlayer(){
  		return this.isValidPlayer;
  	}

  	/**
  	 * Returns chosen player.
  	 * @return Player 
  	 */
  	public Player getValidPlayer(){
  		return this.validPlayer;
  	}

  	/**
  	 * clean playerDeck function that returns the Hand Deck of a player.
  	 * @return Player Hand Deck
  	 */
  	protected List<Card> getPlayerDeck(Player player) {
  		return player.getHand().getDeck();
  	}

  	/**
  	 * clean seeHand function that prints the player Hand on screen.
  	 */
  	protected void seeHand(Player player){
  		if (player.getHand().isEmpty())
  			printout("Player does not hold any cards in hand!");
  		else
  			printout(player.getHand().toString());
  	}

  	/**
  	 * clean printout function to print to the console.
  	 * @param toPrint The string to be printed.
  	 */
  	private void printout(String toPrint) {
  		System.out.println(toPrint);
  	}
}