package forbiddenIsland.player;

import forbiddenIsland.board.IslandTile;
import forbiddenIsland.card.Card;
import java.util.List;
import java.util.ArrayList;


// Abstract class representing player classes
public abstract class Player {

	// Player position, deck, and count of actions taken. 
	private IslandTile islandTile = new IslandTile();
	private List<Card> handDeck = new ArrayList<Card>();
	private int actionsTaken; 
	
	// Constructor
	Player(IslandTile islandTile){
		this.setIslandTile(islandTile);
		this.setHandDeck(null);
		this.setActionsTaken(0);
	}
	//-----------------------------------
	// Methods
	//-----------------------------------
	public void move(IslandTile islandTile) {}
	public void useCard() {}
	public void shoreUp() {}
	public void giveTreasurerCard() {}
//	private void captureTreasure() {} // Helper method for useCard
	
	/* toString Method*/
	public String toString() {
		return "Adventurer: "+this.getClass()
		+"\nPlayer IslandTile:"+this.getIslandTile()
		+", No. Actions Taken:"+this.getActionsTaken()
		+"\nHand Deck:"+this.getHandDeck().toString();
	}
	//-----------------------------------
	// Setters
	//-----------------------------------
	private void setIslandTile(IslandTile islandTile) {
		this.islandTile=islandTile;
	}
	private void setHandDeck(List<Card> handDeck) {
		this.handDeck=handDeck;
	}
	private void setActionsTaken(int actionsTaken) {
		this.actionsTaken=actionsTaken;
	}
	//------------------------------------
	// Getters
	//------------------------------------
	private IslandTile getIslandTile(){
		return this.islandTile;
	}
	private List<Card> getHandDeck(){
		return this.handDeck;
	}
	private int getActionsTaken(){
		return this.actionsTaken;
	}
}

