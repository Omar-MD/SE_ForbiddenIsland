package forbiddenIsland.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import forbiddenIsland.adventurer.Engineer;
import forbiddenIsland.adventurer.Pilot;
import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.board.Position;
import forbiddenIsland.card.Card;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.enums.SpecialCardEnums;
import forbiddenIsland.gameplay.GameController;
import forbiddenIsland.gameplay.Treasure;
import forbiddenIsland.gameplay.WaterMeter;
import forbiddenIsland.gameplay.WinObserver;
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
    	this.thisPlayer     = thisPlayer;
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

        printout("It is "+thisPlayer.getName()+"'s (Player "+ thisPlayer.getChar() +") turn! Please return.");
        inputScanner.nextLine(); // Make player press return to confirm turn start
        
        controller.showBoard();
		printout("Adventurer: "+thisPlayer.getRole().toString());
		printout("WaterLevel: "+WaterMeter.getInstance().getWaterLevel());
        //----------------------------
	    // Perform at most 3 actions
	    //----------------------------
		while (!turnOver && count<3) {

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
			
			if(!controller.getGameFinish()){
				// Increment count if action is valid
				count += (isActionValid())? 1: 0;
				setValidAction(false);
				printout("\nActions taken: "+count);
			}else{
				return;
			}
        }

        //----------------------------
	    // Draw 2 treasure Cards
        //----------------------------
		if (pickUpTwoCards()) {
			// If method returns true, game is finished
			return;
		}
            
        //----------------------------
	    // Draw # Flood cards equal to Water level
        //----------------------------
        List<Card> drawnFloodCards = controller.drawFloodCards();
        
        //----------------------------
	    // Flip Island Tiles
        //----------------------------
        controller.flipIslandTiles(drawnFloodCards);
        if(controller.getGameFinish()) return;

        // End turn
        printout("\nYour turn has ended.\n");
	}

    /**
     * Pick up two treasure cards and if game is finished during this, return true.
     * @return boolean 
     */
    private boolean pickUpTwoCards(){
    	int i = 0;
    	while (i < 2) {
    		controller.drawTreasureCard(thisPlayer);
    		// Check if need to discard or use a card
    		if(controller.getGameFinish()) return true;
            checkForDiscard(thisPlayer);
            i++;
    	}
        return false;
    }

    /**
     * Discard extra card in player deck. If special card is chosen, it can be used first.
	 * @param player the player
     */
    private void checkForDiscard(Player player){
    	if(getPlayerDeck(player).size()>5) {
    		printout("\n" + player.getName()+" (Player "+ player.getChar() + ") has more than 5 cards in hand.");
    		printout("\nWhich card would you like to discard? :");
    		printout("\nNOTE: If you choose to discard a special card, you may use the card first.");
    		seeHand(player);

    		// Find Valid Card in deck
    		Card card = new CardView(player).findCard();

    		if (card instanceof SpecialCard) {
    			boolean validInput = false;
    			while (!validInput) {
    				printout("\nWould you like to use this special card? : (Yes or No)");
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
    					printout("\nInvalid Input. Please enter 'Yes' or 'No'");
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
    			printout("\nWhich player hand would you like to see? :");
    			printout(team.printOtherPlayers(thisPlayer));

    	    	// Get a player from input and view the hand
    	    	seeHand(findOtherPlayer());
    		} else if (userInput == 2) {
    			printout("\nWhich player would like to use a special card? :");
    			printout(team.printOtherPlayers(thisPlayer));

    	    	// Get a player from input and try use a special card
				tryUseSpecialCard(findOtherPlayer());
				if(controller.getGameFinish()) returnCall = true;
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
    	printout("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is on "+ playerPawnTileName());
    	printout("\nWhere would you like to move? (For Example: SIG)");
    	boolean didMove = false;

    	while (!didMove) {
    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();

    		// Try move only once
    		didMove = true;

    		// Move to new Island Tile
    		if(controller.requestMove(thisPlayer, validTile)){
    			printout("\nPlayer "+thisPlayer.getName()+" successfully moved to "
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
    	printout("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is on "+ playerPawnTileName());

    	printout("\nWhich Island would you like to shore up? :");

    	while (!didShoreUp) {
    		// Find Valid Tile on board and add to list
    		shoredTiles.add(new TileView().findTile());

    		if(thisPlayer.getRole() instanceof Engineer){
    			while (!validInput) {
    				printout("\nWould you like to shore up another tile? : (Yes or No)");
    				String response = inputScanner.nextLine();
    				switch (response.toUpperCase()) {
    				case "YES":
    					validInput = true;
    					printout("\nWhich Island would you like to shore up? :");
    					shoredTiles.add(new TileView().findTile());
    					break;
    				case "NO":
    					validInput = true;
    					break;
    				default: 
    					printout("\nInvalid Input. Please enter 'Yes' or 'No'");
    				}
    			}
    		}

    		// Only attempt to ShoreUp once
    		didShoreUp = true;

    		// Shore Up Two Island Tile
    		if(shoredTiles.size() == 2) {
    			if(controller.requestShoreUp(thisPlayer, shoredTiles.get(0))){
    				printout("\nPlayer "+thisPlayer.getName()+" successfully Shored Up "
    						+ getName(shoredTiles.get(0)));
    				action=true;
    			}
    			if(controller.requestShoreUp(thisPlayer, shoredTiles.get(1))) {
    				printout("\nPlayer "+thisPlayer.getName()+" successfully Shored Up "
    						+ getName(shoredTiles.get(1)));
    				action=true;
    			}
    		} else {
    			// Shore Up single Tile
    			if(controller.requestShoreUp(thisPlayer, shoredTiles.get(0))) {
    				printout("\nPlayer "+thisPlayer.getName()+" successfully Shored Up "
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
    	printout("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is on "+ playerPawnTileName());
    	printout("\nWhich Treasure card would you like to give? :");
    	List<Card> tCards = thisPlayer.getHand().getTreasureCards();
    	printout(thisPlayer.getHand().printCards(tCards));

    	// Find Valid Card in deck
    	TreasureCard card = new CardView(thisPlayer).findTreasureCard();

    	printout("\nWhich player would you like to give a Treasure Card to? :");
    	printout(team.printOtherPlayers(thisPlayer));
    	boolean didGiveCard = false;

    	while (!didGiveCard) {
    		// Find the selected teamMate
    		Player teamMate = findOtherPlayer();

    		// Only attempt to give card once
    		didGiveCard = true; 

    		// Give Card
    		if(controller.requestGiveTreasureCard(thisPlayer, card, teamMate)){
    			checkForDiscard(teamMate);
    			printout("\nPlayer "+thisPlayer.getName()+" successfully gave "
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
    		printout("\nPlayer "+thisPlayer.getName()+" successfully captured "
    				+team.getLastTreasure().toString());
    		action = true;
    	}
    }

    //----------------------------
    // Special actions
    //----------------------------
    /**
     * Try use Special card.
	 * @param player
     */
    public void tryUseSpecialCard(Player player){
    	if(!player.getHand().hasSpecialCard(SpecialCardEnums.SANDBAGS) 
    			&& !player.getHand().hasSpecialCard(SpecialCardEnums.HELICOPTER_LIFT)){
    		printout("Player has no special card");
            return;
        }
    	printout("\nWhich special card would you like to use? :");
    	List<Card> sCards = player.getHand().getSpecialCards();
    	printout(player.getHand().printCards(sCards));

    	// Find Valid Card in deck
    	SpecialCard card = new CardView(player).findSpecialCard();

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
	 * @param player
     */
    private void tryUseSandbagsCard(Player player){
    	printout("\n"+player.getName()+" (Player "+ player.getChar() +") is on "+ playerPawnTileName());
    	printout("\nWhich Island would you like to shore up? :");
    	boolean didShoreUp = false;

    	while (!didShoreUp) {
    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();

			// Only attempt to ShoreUp once
			didShoreUp = true;
			
    		// Use Sandbags Card
    		if (controller.requestUseSandbagsCard(player, validTile)) {
    			printout("\nPlayer "+player.getName()+" successfully used Sandbags Card to Shore Up "
				+ getName(validTile));
    		}
    	}
    }

    /**
     * Try use HelicopterLift card.
	 * @param player
     */
    private void tryUseHelicopterLiftCard(Player player){
 		// CheckWin Conditions
    	controller.notifyAllObservers();
    	if(WinObserver.getInstance().getReadyToFly()){
			WinObserver.getInstance().gameWin();
			return;
		}

    	boolean didFly = false;

    	while (!didFly) {
    		printout("\n"+player.getName()+" (Player "+ player.getChar() +") is on "+ playerPawnTileName());
        	printout("\nWhich players would you like to lift off?: (Please input one player character at a time)");

        	List<Player> flyingPlayers = findPlayers();
        	printout("\nWhere would you like to fly? :");

    		// Find Valid Tile on board
    		IslandTile validTile = new TileView().findTile();
			
			// Only attempt to fly once
			didFly = true;

    		// Fly to new Island Tile
    		controller.requestUseHelicopterLiftCard(player, flyingPlayers,validTile);
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
    	printout("\n"+thisPlayer.getName()+" (Player "+ thisPlayer.getChar() +") is escaping a sinking tile!");
		Board.getInstance().printBoard();

		// Valid Escape Tiles
		List<IslandTile> escapeTiles = escapeOptions(thisPlayer);
		
		if(!escapeTiles.isEmpty()){
			printout("\nWhere would you like to escape? :");
			printIslandTiles(escapeTiles);
		}else{
			return;
		}
		boolean didEscape = false;

    	while (!didEscape && !escapeTiles.isEmpty()) {
    		// Find Valid Escape Tile
    		IslandTile validTile = findEscapeTile(escapeTiles);

    		if(thisPlayer.getRole() instanceof Pilot) {
    			if(controller.requestEscapeSinkingTileByFlight(thisPlayer, validTile)) {
    				printout("\nPlayer "+thisPlayer.getName()+" has successfully flown to "
							+getName(validTile));
					didEscape = true;
    			}
    		} else {
    			// Swim to new Island Tile
    			if(controller.requestEscapeSinkingTileBySwim(thisPlayer, validTile)) {
    				printout("\nPlayer "+thisPlayer.getName()+" successfully swam to "
							+getName(validTile));
					didEscape = true;
    			}
    		}
    	}
	}
	
	/**
     * Method to select player's valid escape Tile
	 * @param escapeTiles List of IslandTiles
     */
	private IslandTile findEscapeTile(List<IslandTile> escapeTiles){
		IslandTile validTile = null;
		boolean isValidTile = false;
		int tileIndex = 0;

        // Obtain Valid Tile index
        while(!isValidTile){
            String userString = inputScanner.nextLine();	
            try {tileIndex = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {
            	printout("\n" + userString + " is not a valid index!");
            	continue;
            }
            if((tileIndex>= 1) && (tileIndex<=escapeTiles.size())){
                printout("\nValid Escape Tile chosen");
                validTile = escapeTiles.get(tileIndex-1);
                isValidTile = true;
            }
            else
                printout("Incorrect Tile index");
        }
        return validTile;
	}

	/**
  	 * Find players escape Island Tiles.
	 * @param Player player
  	 * @return List of IslandTiles 
  	 */
	private List<IslandTile> escapeOptions(Player player){
		Board board = Board.getInstance();
		switch(player.getRole().toString()){
			case "Pilot": 
				return getValidTiles();
			case "Diver":
				return board.getNearestTiles(player.getPawn().getPawnTile());
			case "Explorer":
				List<IslandTile> escapeTiles = new ArrayList<IslandTile>();
				escapeTiles = board.getDiagonals(player.getPawn().getPawnTile());
				escapeTiles.addAll(board.getAdjacent(player.getPawn().getPawnTile()));
				return escapeTiles;
			default:
				return board.getAdjacent(player.getPawn().getPawnTile());
		}
	}

	/**
  	 * Find all non-sunk Island Tiles.
  	 * @return List of valid IslandTiles 
  	 */
	private List<IslandTile> getValidTiles(){
		List<IslandTile> validTiles = new ArrayList<IslandTile>();
		for(Position pos: Board.getInstance().getValidTilePositions()){
			IslandTile tile = Board.getInstance().getTile(pos);
			if(!tile.isSunk()){
				validTiles.add(tile);
			}
		}
		return validTiles;
	}

	/**
	 * Print List of IslandTiles
	 * @param listTiles list of IslandTiles
	 */
	private void printIslandTiles(List<IslandTile> listTiles) {
		List<String> tiles = new ArrayList<String>();
		for(int i=1; i < listTiles.size()+1; i++){
			 tiles.add("\n["+ i +"]"+" : " + listTiles.get(i-1).toString());
		}
		printout(String.join(" ", tiles));
	}
	
    /**
  	 * Find selected other player.
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
  				printout("\nValid player character chosen");
  				validPlayer = team.getPlayer(validPlayerNum-1);
  				isValidPlayer = true;
  			}
  			else {
  				printout("\nIncorrect player character chosen.");
  				printout("\nPlease choose a player character from the above options: (Eg: 2)");
  			}
  		}
  		isValidPlayer = false;
  		validPlayerNum = 0;
  		return validPlayer;
  	}

  	/**
  	 * Find selected players.
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
  				printout(p.toString());
  			}
  			printout("[" + (findMax(remainingPlayerNums)+1) +"] : End choosing players.");

  			while(!userCall && !isValidPlayer){
  				validPlayerNum = findValidPlayerNum();
  				// If validPlayerNum remains 0, it means user input was invalid
  				if (validPlayerNum == 0) {
  					continue;
  				}
  				// Player cannot choose him/her self and must be within the PlayerList range
  				else if(remainingPlayerNums.contains(validPlayerNum)) {
  					printout("\nValid player character chosen");
  					validPlayer = remainingPlayers.remove(remainingPlayerNums.indexOf(validPlayerNum));
  					selectedPlayers.add(validPlayer);
  					remainingPlayerNums.remove(Integer.valueOf(validPlayerNum));
  					isValidPlayer = true;
  				}
  				else if(validPlayerNum == (findMax(remainingPlayerNums)+1)) {
  					printout("\nEnd choosing players and return");
  					userCall = true;
  				}
  				else {
  					printout("\nIncorrect player character chosen.");
  					printout("\nPlease choose a player character from the above options: (Eg: 2)");
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
  			printout("\n" + userString + " is not a valid input!");
  		}
  		return validPlayerNum;
  	}

  	/**
  	 * Returns the maximum of an Integer list or 0 if list is empty.
  	 * @param Integer list
  	 * @return Integer maximum 
  	 */
  	public int findMax(List<Integer> list){
  		if (list.isEmpty())
  			return 0;
  		return Collections.max(list);
  	}

    /**
     * Clean playerPawnTile function that returns the name of a players pawn Tile.
     * @return Player pawn Tile name
     */
  	private String playerPawnTileName() {
          return getName(thisPlayer.getPawn().getPawnTile());
      }
   
  	/**
  	 * Clean getName function that returns the String name of an IslandTile.
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
  	 * @return boolean
  	 */
  	public boolean isActionValid(){
  		return this.action;
  	}

  	/**
  	 * Set boolean action.
  	 * @param boolean
  	 */
  	public void setValidAction(boolean bool){
  		this.action = bool;
  	}

  	/**
  	 * Returns if chosen players are valid.
  	 * @return Boolean
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
  	 * @param player Player
	 * @return Player deck
  	 */
  	protected List<Card> getPlayerDeck(Player player) {
  		return player.getHand().getDeck();
  	}

  	/**
  	 * clean seeHand function that prints the player Hand on screen.
	 * @param player 
  	 */
  	protected void seeHand(Player player){
  		if (player.getHand().isEmpty())
  			printout("Player does not hold any cards in hand!");
  		else
  			printout(player.getHand().toString());
  	}

  	/**
  	 * Clean printout function to print to the console.
  	 * @param toPrint The string to be printed.
  	 */
  	private void printout(String toPrint) {
  		System.out.println(toPrint);
  	}
}