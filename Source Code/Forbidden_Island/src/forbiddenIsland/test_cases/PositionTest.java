package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import forbiddenIsland.board.Position;

/**
 * JUnit test cases for the Position class to test the main method.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class PositionTest {

	@Test
	public void testGetManhattan_1() {
		Position pos_1 = new Position(1,1);
		Position pos_2 = new Position(1,4);
		assertEquals(3,pos_1.getManhattan(pos_2),"Checking the manhattan distance for the first test example");
	}

	@Test
	public void testGetManhattan_2() {
		Position pos_1 = new Position(1,1);
		Position pos_2 = new Position(4,4);
		assertEquals(6,pos_1.getManhattan(pos_2),"Checking the manhattan distance for the second test example");
	}

	@Test
	public void testGetManhattan_3() {
		Position pos_1 = new Position(1,1);
		Position pos_2 = new Position(3,5);
		assertEquals(6,pos_1.getManhattan(pos_2),"Checking the manhattan distance for the third test example");
	}

}
