package forbiddenIsland.card;

// Abstract class depicting card object
public abstract class Card{

	// Card name
	private Enum name;
	
	// Constructor
	Card(Enum name){
		this.name = name;
	}
	
	// Getter
	public Enum getName(){
		return this.name;
	}
}
