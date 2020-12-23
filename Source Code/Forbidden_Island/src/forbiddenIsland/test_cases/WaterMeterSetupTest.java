package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import forbiddenIsland.setup.WaterMeterSetup;

/**
 * JUnit test cases for the WaterMeterSetup class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class WaterMeterSetupTest {

	@Test
	public void test() {
		WaterMeterSetup waterMeterSetup1 = new WaterMeterSetup();
		assertEquals(0,waterMeterSetup1.setGameDifficulty("1"), "Converts users string input to integer minus 1");
		assertTrue(waterMeterSetup1.getValidDifficulty(), "Game difficulty must be between 0 and 3");

		WaterMeterSetup waterMeterSetup2 = new WaterMeterSetup();
		assertEquals(2,waterMeterSetup2.setGameDifficulty("3"), "Converts users string input to integer minus 1");
		assertTrue(waterMeterSetup2.getValidDifficulty(), "Game difficulty must be between 0 and 3");

		WaterMeterSetup waterMeterSetup3 = new WaterMeterSetup();
		assertEquals(3,waterMeterSetup3.setGameDifficulty("4"), "Converts users string input to integer minus 1");
		assertTrue(waterMeterSetup3.getValidDifficulty(), "Game difficulty must be between 0 and 3");

		WaterMeterSetup waterMeterSetup4 = new WaterMeterSetup();
		assertEquals(6,waterMeterSetup4.setGameDifficulty("7"), "Converts users string input to integer minus 1");
		assertFalse(waterMeterSetup4.getValidDifficulty(), "Game difficulty must be between 0 and 3");
	}

}
