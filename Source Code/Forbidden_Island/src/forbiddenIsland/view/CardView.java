package forbiddenIsland.view;

import java.util.Scanner;

import forbiddenIsland.card.Card;
import forbiddenIsland.card.SpecialCard;
import forbiddenIsland.card.TreasureCard;
import forbiddenIsland.player.Player;

/**
 * CardView class finds selected card in players hand.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class CardView {
    
    //------------------------
	// Variables
    //------------------------
    private Player   player;
    private GameView gameView;
    private Scanner  inputScanner;
    private Card     validCard;
    private int      cardIndex;
    //------------------------
	// Constructor
	//------------------------
    public CardView(Player thisPlayer){
    	this.gameView     = GameView.getInstance();
    	this.inputScanner = gameView.getScanner();
        this.player       = thisPlayer;
        this.cardIndex    = 0;
    }

    /**
     * Method Finds Chosen card
     * @param Scanner input scanner
     * @return selected Card
     */
    public Card findCard(){
        boolean isValidCard = false;
        // Obtain Valid Card index
        while(!isValidCard){
            String userString = inputScanner.nextLine();	
            try {cardIndex = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {
            	printout("\n" + userString + " is not a valid input!");
            	continue;
            }
            if((cardIndex>= 1) && (cardIndex<=player.getHand().getDeck().size())){
                printout("\nValid Card chosen");
                validCard = player.getHand().getCard(cardIndex-1);
                isValidCard = true;
            }
            else
                printout("Incorrect card index");
        }
        return validCard;
    }

    /**
     * Method Finds Chosen Treasure card from Player Hand
     * @param Scanner input scanner
     * @return selected Treasure Card
     */
    public TreasureCard findTreasureCard(){
        boolean isValidCard = false;
        // Obtain Valid Card index
        while(!isValidCard){
            String userString = inputScanner.nextLine();	
            try {cardIndex = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {
            	printout("\n" + userString + " is not a valid input!");
            	continue;
            }
            if((cardIndex >= 1) && (cardIndex <= player.getHand().getTreasureCards().size())){
                printout("\nValid Card chosen");
                validCard = player.getHand().getTreasureCards().get(cardIndex-1);
                isValidCard = true;
            }
            else
                printout("\nIncorrect card index");
        }
        return (TreasureCard) validCard;
    }

    /**
     * Method Finds Chosen Special card from Player Hand
     * @param Scanner input scanner
     * @return selected Special Card
     */
    public SpecialCard findSpecialCard(){
        boolean isValidCard = false;
        // Obtain Valid Card index
        while(!isValidCard){
            String userString = inputScanner.nextLine();	
            try {cardIndex = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {
            	printout("\n" + userString + " is not a valid input!");
            	continue;
            }
            if((cardIndex >= 1) && (cardIndex <= player.getHand().getSpecialCards().size())){
                printout("\nValid Card chosen");
                validCard = player.getHand().getSpecialCards().get(cardIndex-1);
                isValidCard = true;
            }
            else
                printout("\nIncorrect card index");
        }
        return (SpecialCard) validCard;
    }

     /**
     * Method returns Chosen card index
     * @return card index
     */
    public int getValidCardIndex(){
        return (this.cardIndex-1);
    }

     /**
     * Method returns Chosen card
     * @return Chosen valid card
     */
    public Card getValidCard(){
        return this.validCard;
    }

    /**
   	 * clean printout function to print to the console.
   	 * @param toPrint The string to be printed.
   	 */
    private void printout(String toPrint) {
    	System.out.println(toPrint);
    }
}
