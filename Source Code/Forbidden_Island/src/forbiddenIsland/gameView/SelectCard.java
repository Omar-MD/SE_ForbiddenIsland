package forbiddenIsland.gameView;

import java.util.Scanner;

import forbiddenIsland.card.Card;
import forbiddenIsland.player.Player;

/**
 * SelectCard class finds selected card in players hand.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class SelectCard {
    
    //------------------------
	// Variables
    //------------------------
    private Player        player;
    private Card       validCard;
    private int        cardIndex;
    //------------------------
	// Constructor
	//------------------------
    public SelectCard(Player thisPlayer){
        this.player    = thisPlayer;
        this.validCard = null;
        this.cardIndex = 0;
    }

    /**
     * Method Finds Chosen card
     * @param Scanner input scanner
     */
    public void findCard(Scanner inputScanner){
        boolean isValidCard = false;
        // Obtain Valid Card index
        while(!isValidCard){
            String userString = inputScanner.nextLine();	
            try {cardIndex = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {continue;}
            if((cardIndex>= 1) && (cardIndex<=player.getHand().getDeck().size())){
                System.out.println("\nValid Card chosen");
                validCard = player.getHand().getCard(cardIndex-1);
                isValidCard = true;
            }
            else
                System.out.println("Incorrect card index");
        }
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
}
