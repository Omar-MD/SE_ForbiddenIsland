package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import forbiddenIsland.setup.PlayerSetup;

/**
 * JUnit test cases for the PlayerSetup class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class PlayerSetupTest {

	@Test
	void testSetNumPlayers() {
		PlayerSetup testPlayerSetup1 = new PlayerSetup();
		assertEquals(1,testPlayerSetup1.setNumPlayers("1"), "Converts users string input to int");
		assertFalse(testPlayerSetup1.getValidNumPlayers(), "Must be between 2 and 4");

		PlayerSetup testPlayerSetup2 = new PlayerSetup();
		assertEquals(2,testPlayerSetup2.setNumPlayers("2"), "Converts users string input to int");
		assertTrue(testPlayerSetup2.getValidNumPlayers(), "Must be between 2 and 4");

		PlayerSetup testPlayerSetup3 = new PlayerSetup();
		assertEquals(4,testPlayerSetup3.setNumPlayers("4"), "Converts users string input to int");
		assertTrue(testPlayerSetup3.getValidNumPlayers(), "Must be between 2 and 4");

		PlayerSetup testPlayerSetup4 = new PlayerSetup();
		assertEquals(7,testPlayerSetup4.setNumPlayers("7"), "Converts users string input to int");
		assertFalse(testPlayerSetup4.getValidNumPlayers(), "Must be between 2 and 4");
	}

}
