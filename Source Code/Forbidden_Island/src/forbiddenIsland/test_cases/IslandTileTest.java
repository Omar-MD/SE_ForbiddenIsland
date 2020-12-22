package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.board.Position;
import forbiddenIsland.enums.StateEnums;

/**
 * JUnit test cases for the IslandTile class to test the main method.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class IslandTileTest {

	private Board testBoard;

	@BeforeEach
	public void setUp() throws Exception {
		testBoard = Board.getInstance();
	}

	@AfterEach
	public void tearDown() throws Exception {
		testBoard.destroyMe();
	}

	@Test
	public void testFlip() {
		// Tile is in DRY state at the beginning
		IslandTile tileUnderTest = testBoard.getTile(new Position(1,1));
		tileUnderTest.flip();

		// Once flipped, it becomes FLOODED
		assertEquals(StateEnums.FLOODED,tileUnderTest.getState(),"Checking tile state for dry tile that was flipped");

		// Flip the tile from flooded to sunk
		tileUnderTest.flip();
		assertEquals(StateEnums.SUNK,tileUnderTest.getState(),"Checking tile state for flooded tile that was flipped");

	}

}
