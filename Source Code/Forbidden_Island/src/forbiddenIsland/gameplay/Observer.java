package forbiddenIsland.gameplay;

/**
 * Abstract Observer class to monitor Forbidden Island game status.
 * @author Jithin James and Omar Duadu
 * @version 1.0
 *
 */
public abstract class Observer {
    //----------------------------
    // GamePlay status  
    //----------------------------
    protected GameController gameController;   
    
    //----------------------------
    // Update method
    //----------------------------
    public abstract boolean update();
}