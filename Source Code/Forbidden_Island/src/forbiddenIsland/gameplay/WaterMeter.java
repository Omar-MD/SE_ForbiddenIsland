package forbiddenIsland.gameplay;

/**
 * Class depicting Water meter object
 * in the game of Forbidden Island.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public class WaterMeter {

	//------------------------
	// Setup Singleton instance and Variables
	//------------------------
	private static WaterMeter instance = null;
	private int waterLevel;

	//-----------------------------------
	// Get Instance of Singleton
	//-----------------------------------
	/**
	 * getInstance method returns single instance of WaterMeter.
	 * @return instance singleton WaterMeter object.
	 */
	public static WaterMeter getInstance() {
		if(instance == null)
			instance = new WaterMeter("Normal");
		return instance;
	}

	//------------------------
	// Constructor
	//------------------------
	/**
	 * Default Water Meter Constructor.
	 * Initializes water level to 1. 
	 */
	private WaterMeter(String difficulty) {
		setWaterLevel(difficulty);
	}

	//-----------------------
	// Method
	//-----------------------
	/**
	 * Increment Water level by 1.  
	 */
	public void raiseWaterLevel() {
		this.waterLevel = waterLevel+1;
		System.out.println("***   WATER LEVEL AT "+ waterLevel +"   ***");
	}

	//-----------------------
	// Setter
	//-----------------------
	/**
	 * Set Water level to its associated difficulty 
	 * @param difficulty Game Difficulty  
	 */
	public void setWaterLevel(String difficulty){
		switch(difficulty) {
			case "Novice":
				this.waterLevel=1;
				break;
			case "Normal":
				this.waterLevel=2;
				break;
			case "Elite":
				this.waterLevel=3;
				break;
			case "Legendary":
				this.waterLevel=4;
				break;
			default:
				System.out.println("Error(setWaterLevel): Incorrect Game difficulty");
		}
	}
	
	//-----------------------
	// Getter
	//-----------------------
	/**
	 * Return Water meter level 
	 * @return waterLevel Water level 
	 */
	public int getWaterLevel(){
		return this.waterLevel;
	}

	/**
	 * Return number of cards to draw
	 * @return cardsToDraw Integer number of cards to draw 
	 */
	public int getCardsToDraw(){
		int cardsToDraw = 0;
		switch(waterLevel){
		case 1:
		case 2:  cardsToDraw = 2; break;
		case 3:
		case 4:
		case 5:  cardsToDraw = 3; break;
		case 6:
		case 7:  cardsToDraw = 4; break;
		case 8:
		case 9:  cardsToDraw = 5; break;
		default: System.out.println("\nCase Error in WaterMeter.getCardsToDraw()");
		}
		return cardsToDraw;
	}
}
