package forbiddenIsland.gameplay;

import java.util.Scanner;

import forbiddenIsland.card.Card;
import forbiddenIsland.player.Player;

public class CardInDeck {
    
    //------------------------
	// Variables
	//------------------------
    private boolean    validCard;
    private int        cardIndex;
    private Player        player;
    
    //------------------------
	// Constructor
	//------------------------
    public CardInDeck(Player thisPlayer){
        this.validCard = false;
        this.cardIndex = 0;
        this.player    = thisPlayer;
    }

    public void findCard(Scanner inputScanner){
        // Obtain Valid Card index
        while(!validCard){
            String userString = inputScanner.nextLine();	
            try {cardIndex = Integer.parseInt(userString);} 
            catch (NumberFormatException e) {continue;}

            if((cardIndex>= 0) && (cardIndex<=player.getHand().getDeck().size())){
                System.out.println("\nValid Card chosen");
                validCard = true;
            }    
            else
                System.out.println("Incorrect card index");
        }
    }

    public int getValidCardIndex(){
        return this.cardIndex;
    }
    
    public boolean isValidCard(){
        return this.validCard;
    }

    public Card getValidCard(){
        return player.getHand().getCard(getValidCardIndex());
    }
}
