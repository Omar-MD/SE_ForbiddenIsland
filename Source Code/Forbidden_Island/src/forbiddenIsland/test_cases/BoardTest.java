package forbiddenIsland.test_cases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import forbiddenIsland.board.Board;
import forbiddenIsland.board.IslandTile;
import forbiddenIsland.board.Position;
import forbiddenIsland.enums.StateEnums;

/**
 * JUnit test cases for the Board class to test the main methods.
 * 
 * @author Jithin James and Omar Duadu
 * @version 1.0
 */
public class BoardTest {
	
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
	public void testGetValidTileByPos() {
		List<Position> validTilePositions = testBoard.getValidTilePositions();
		assertEquals(24,validTilePositions.size(),"Check if validTilePositions contains 24 valid tile positions");

		for (Position pos: validTilePositions) {
			assertNotNull(testBoard.getTile(pos),"Check if tile is obtained for valid tile position");
		}

		Position nullPos = new Position(0, 0);
		assertNull(testBoard.getTile(nullPos),"Check if null is obtained for invalid tile position");
	}

	@Test
	public void testGetDiagonals() {
		List<IslandTile> expectedTiles = new ArrayList<IslandTile>();
		// Expected tiles should contain the expected diagonal 
		// tiles to tile at position (3,3)
		expectedTiles.add(testBoard.getTile(new Position(4,4)));
		expectedTiles.add(testBoard.getTile(new Position(2,4)));
		expectedTiles.add(testBoard.getTile(new Position(2,2)));
		expectedTiles.add(testBoard.getTile(new Position(4,2)));

		IslandTile tileUnderTest = testBoard.getTile(new Position(3,3));
		List<IslandTile> actualTiles = testBoard.getDiagonals(tileUnderTest);

		assertTrue(expectedTiles.size() == actualTiles.size(), "Expected tiles must have a size equal to tiles obtained from getDiagonals() i.e. 4");
		assertTrue(actualTiles.containsAll(expectedTiles) && expectedTiles.containsAll(actualTiles), "Check if both lists contain the same tiles");

		// Make tile at position (4,4) sunk
		IslandTile tileToBeSunk = testBoard.getTile(new Position(4,4));
		tileToBeSunk.setState(StateEnums.SUNK);
		// Sunk tiles should not be returned by getDiagonals method
		expectedTiles.remove(tileToBeSunk);

		List<IslandTile> actualTilesAfterTileSink = testBoard.getDiagonals(tileUnderTest);

		assertTrue(expectedTiles.size() == actualTilesAfterTileSink.size(), "Expected tiles must have a size equal to tiles obtained from getDiagonals() i.e. 3");
		assertTrue(actualTilesAfterTileSink.containsAll(expectedTiles) && expectedTiles.containsAll(actualTilesAfterTileSink), "Check if both lists contain the same tiles after one of the tiles are sunk");
	}

	@Test
	public void testGetAdjacent() {
		List<IslandTile> expectedTiles = new ArrayList<IslandTile>();
		// Expected tiles should contain the expected adjacent 
		// tiles to tile at position (3,3)
		expectedTiles.add(testBoard.getTile(new Position(3,4)));
		expectedTiles.add(testBoard.getTile(new Position(4,3)));
		expectedTiles.add(testBoard.getTile(new Position(3,2)));
		expectedTiles.add(testBoard.getTile(new Position(2,3)));

		IslandTile tileUnderTest = testBoard.getTile(new Position(3,3));
		List<IslandTile> actualTiles = testBoard.getAdjacent(tileUnderTest);

		assertTrue(expectedTiles.size() == actualTiles.size(), "Expected tiles must have a size equal to tiles obtained from getAdjacent() i.e. 4");
		assertTrue(actualTiles.containsAll(expectedTiles) && expectedTiles.containsAll(actualTiles), "Check if both lists contain the same tiles");

		// Make tile at position (3,4) sunk
		IslandTile tileToBeSunk = testBoard.getTile(new Position(3,4));
		tileToBeSunk.setState(StateEnums.SUNK);
		// Sunk tiles should not be returned by getAdjacent method
		expectedTiles.remove(tileToBeSunk);

		List<IslandTile> actualTilesAfterTileSink = testBoard.getAdjacent(tileUnderTest);

		assertTrue(expectedTiles.size() == actualTilesAfterTileSink.size(), "Expected tiles must have a size equal to tiles obtained from getAdjacent() i.e. 3");
		assertTrue(actualTilesAfterTileSink.containsAll(expectedTiles) && expectedTiles.containsAll(actualTilesAfterTileSink), "Check if both lists contain the same tiles after one of the tiles are sunk");
	}

	@Test
	public void testGetNearestTiles() {
		// Tile under Test
		IslandTile tileUnderTest = testBoard.getTile(new Position(3,5));

		// Using Manhattan distance, the nearest tiles to tile at position (3,5) should be: 
		// Adjacent tiles at (2,5) and (3,4)
		List<IslandTile> expectedTiles_1 = new ArrayList<IslandTile>();
		expectedTiles_1.add(testBoard.getTile(new Position(2,5)));
		expectedTiles_1.add(testBoard.getTile(new Position(3,4)));

		List<IslandTile> actualTiles_1 = testBoard.getNearestTiles(tileUnderTest);

		assertTrue(expectedTiles_1.size() == actualTiles_1.size(), "Expected tiles must have a size equal to tiles obtained from getNearestTiles() i.e. 2");
		assertTrue(actualTiles_1.containsAll(expectedTiles_1) && expectedTiles_1.containsAll(actualTiles_1), "Check if both lists contain the same tiles");
		
		// Make all the adjacent and diagonal tiles of tile at position (3,5) sunk
		// Adjacent tiles are at (2,5) and (3,4)
		// Diagonal tiles are at (2,4) and (4,4)
		testBoard.getTile(new Position(3,4)).setState(StateEnums.SUNK);
		testBoard.getTile(new Position(2,5)).setState(StateEnums.SUNK);
		testBoard.getTile(new Position(4,4)).setState(StateEnums.SUNK);
		testBoard.getTile(new Position(2,4)).setState(StateEnums.SUNK);

		// Using Manhattan distance, the nearest tiles to tile at position (3,5) should be 
		// just the tile at position (3,3) at a distance of 2 tile units away
		List<IslandTile> expectedTiles_2 = new ArrayList<IslandTile>();
		expectedTiles_2.add(testBoard.getTile(new Position(3,3)));

		List<IslandTile> actualTiles_2 = testBoard.getNearestTiles(tileUnderTest);

		assertTrue(expectedTiles_2.size() == actualTiles_2.size(), "Expected tiles must have a size equal to tiles obtained from getNearestTiles() i.e. 1");
		assertTrue(actualTiles_2.containsAll(expectedTiles_2) && expectedTiles_2.containsAll(actualTiles_2), "Check if both lists contain the same tiles");
	}

	@Test
	public void testGetNearestTilesAllSunkExceptOne() {
		// Make all the tiles on the board sink except one at position (1,1)
		for (Position pos: testBoard.getValidTilePositions()) {
			if (!pos.equals(new Position(1,1))) {
				testBoard.getTile(pos).setState(StateEnums.SUNK);
			}
		}

		// The only nearest tile on the board is at (1,1)
		List<IslandTile> expectedTiles = new ArrayList<IslandTile>();
		expectedTiles.add(testBoard.getTile(new Position(1,1)));

		// Tile Under Test 1
		IslandTile tileUnderTest_1 = testBoard.getTile(new Position(3,5));
		List<IslandTile> actualTiles_1 = testBoard.getNearestTiles(tileUnderTest_1);

		assertTrue(expectedTiles.size() == actualTiles_1.size(), "Expected tiles must have a size equal to tiles obtained from getNearestTiles() i.e. 1");
		assertTrue(actualTiles_1.containsAll(expectedTiles) && expectedTiles.containsAll(actualTiles_1), "Check if both lists contain the same tile at position (1,1)");

		// Tile Under Test 2
		IslandTile tileUnderTest_2 = testBoard.getTile(new Position(1,4));
		List<IslandTile> actualTiles_2 = testBoard.getNearestTiles(tileUnderTest_2);

		assertTrue(expectedTiles.size() == actualTiles_2.size(), "Expected tiles must have a size equal to tiles obtained from getNearestTiles() i.e. 1");
		assertTrue(actualTiles_2.containsAll(expectedTiles) && expectedTiles.containsAll(actualTiles_2), "Check if both lists contain the same tile at position (1,1)");
	}
}
