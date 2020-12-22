package forbiddenIsland.test_cases;

/************************************************************************************************************
The only things we didn't test were getters and setters, methods whose only function is to call another method,
or the GamePlay/Do turn methods, where all the logic of the methods were tested in other classes.
************************************************************************************************************/

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(JUnitPlatform.class)
@SelectClasses({
    BoardTest.class,
    IslandTileTest.class,
    PositionTest.class
})

public class RunAllTests {

}
