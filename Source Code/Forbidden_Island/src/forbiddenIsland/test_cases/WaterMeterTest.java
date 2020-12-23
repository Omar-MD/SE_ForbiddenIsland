package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.gameplay.WaterMeter;

/**
 * JUnit test cases for the WaterMeter class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class WaterMeterTest {

	private WaterMeter waterMeter;

	@BeforeEach
	public void setUp() throws Exception {
		waterMeter = WaterMeter.getInstance();
	}

	@AfterEach
	public void tearDown() throws Exception {
		waterMeter.destroyMe();
	}

	@Test
	public void testSetWaterLevel() {
		// WaterMeter is set to Normal difficulty initially
		assertEquals(2,waterMeter.getWaterLevel(),"Initial water level at 2");

		// Set difficulty to Legendary
		waterMeter.setWaterLevel("Legendary");
		assertEquals(4,waterMeter.getWaterLevel(),"Water level set to 4");
	}

	@Test
	public void testGetCardsToDraw() {
		// WaterMeter is set to Normal difficulty initially
		assertEquals(2,waterMeter.getCardsToDraw(),"When water level <= 2, we should pick up 2 cards");

		waterMeter.raiseWaterLevel();
		// Water Level is at 3 now
		assertEquals(3,waterMeter.getCardsToDraw(),"When water level >= 3, we should pick up 3 cards");

		waterMeter.raiseWaterLevel();
		waterMeter.raiseWaterLevel();
		waterMeter.raiseWaterLevel();
		// Water Level is at 6 now
		assertEquals(4,waterMeter.getCardsToDraw(),"When water level >= 6, we should pick up 4 cards");

		waterMeter.raiseWaterLevel();
		waterMeter.raiseWaterLevel();
		// Water Level is at 8 now
		assertEquals(5,waterMeter.getCardsToDraw(),"When water level >= 8, we should pick up 5 cards");
	}

}
