package forbiddenIsland.test_cases;

/************************************************************************************************************
The only things we didn't test were getters and setters, methods whose only function is to call another method,
or the Observer/View/Controller methods, where all the logic of the methods were tested in other classes.
************************************************************************************************************/

import org.junit.runner.RunWith;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(JUnitPlatform.class)
@SelectClasses({
    BoardTest.class,
    IslandTileTest.class,
    PositionTest.class,
    FloodDeckTest.class,
    TreasureDeckTest.class,
    HandTest.class,
    PlayerSetupTest.class,
    CardSetupTest.class,
    WaterMeterTest.class,
    WaterMeterSetupTest.class,
    PlayerTest.class
})

public class RunAllTests {

}
