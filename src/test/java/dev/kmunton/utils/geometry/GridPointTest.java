package dev.kmunton.utils.geometry;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GridPointTest {

  private GridPoint point;
  private int minX, maxX, minY, maxY, minZ, maxZ;

  @BeforeEach
  void setUp() {
    point = new GridPoint(5, 5); // A point in the middle of the grid
    // Define grid bounds
    minX = 0;
    maxX = 10;
    minY = 0;
    maxY = 10;
    minZ = 0; // Not used for 2D grid
    maxZ = 0; // Not used for 2D grid
  }

  @Test
  void getCardinalNeighbors_point_returnsCorrectNeighbors() {
    List<GridPoint> expectedNeighbors = List.of(
        new GridPoint(5, 4), // UP
        new GridPoint(5, 6), // DOWN
        new GridPoint(4, 5), // LEFT
        new GridPoint(6, 5)  // RIGHT
    );

    List<GridPoint> actualNeighbors = point.getCardinalNeighbors();

    assertEquals(expectedNeighbors.size(), actualNeighbors.size());
    assertTrue(actualNeighbors.containsAll(expectedNeighbors));
  }

  @Test
  void getAllNeighbors_point_returnsAllNeighbors() {
    List<GridPoint> expectedNeighbors = List.of(
        new GridPoint(5, 4), // UP
        new GridPoint(5, 6), // DOWN
        new GridPoint(4, 5), // LEFT
        new GridPoint(6, 5), // RIGHT
        new GridPoint(4, 4), // UP_LEFT
        new GridPoint(6, 4), // UP_RIGHT
        new GridPoint(4, 6), // DOWN_LEFT
        new GridPoint(6, 6)  // DOWN_RIGHT
    );

    List<GridPoint> actualNeighbors = point.getAllNeighbors();

    assertEquals(expectedNeighbors.size(), actualNeighbors.size());
    assertTrue(actualNeighbors.containsAll(expectedNeighbors));
  }

  @Test
  void getNeighborGivenDirection_pointAndDirection_returnsCorrectNeighbor() {
    Direction2D direction = Direction2D.UP_LEFT;
    GridPoint expectedNeighbor = new GridPoint(4, 4);

    GridPoint actualNeighbor = point.getNeighborGivenDirection(direction);

    assertEquals(expectedNeighbor, actualNeighbor);
  }

  @Test
  void isInBounds_pointInsideGrid_returnsTrue() {
    GridPoint insidePoint = new GridPoint(3, 7);
    assertTrue(insidePoint.isInBounds(minX, maxX, minY, maxY, minZ, maxZ));
  }

  @Test
  void isInBounds_pointOutsideGrid_returnsFalse() {
    GridPoint outsidePoint = new GridPoint(-1, 11);
    assertFalse(outsidePoint.isInBounds(minX, maxX, minY, maxY, minZ, maxZ));
  }

  @Test
  void isEdge_edgePoint_returnsTrue() {
    GridPoint edgePoint = new GridPoint(0, 5);
    assertTrue(edgePoint.isEdge(minX, maxX, minY, maxY, minZ, maxZ));
  }

  @Test
  void isEdge_nonEdgePoint_returnsFalse() {
    GridPoint nonEdgePoint = new GridPoint(5, 5);
    assertFalse(nonEdgePoint.isEdge(minX, maxX, minY, maxY, minZ, maxZ));
  }

  @Test
  void canMoveGivenDirection_possibleDirection_returnsTrue() {
    Direction2D direction = Direction2D.RIGHT;
    assertTrue(point.canMoveGivenDirection(direction, minX, maxX, minY, maxY, minZ, maxZ));
  }

  @Test
  void canMoveGivenDirection_impossibleDirection_returnsFalse() {
    GridPoint edgePoint = new GridPoint(maxX, maxY);
    Direction2D direction = Direction2D.DOWN_RIGHT;
    assertFalse(edgePoint.canMoveGivenDirection(direction, minX, maxX, minY, maxY, minZ, maxZ));
  }

  @Test
  void manhattanDistance_twoPoints_returnsCorrectDistance() {
    GridPoint point1 = new GridPoint(2, 3);
    GridPoint point2 = new GridPoint(5, 7);

    int expectedDistance = 7; // |2 - 5| + |3 - 7| = 3 + 4 = 7

    int actualDistance = point1.manhattanDistance(point2);

    assertEquals(expectedDistance, actualDistance);
  }

  @Test
  void euclideanDistance_twoPoints_returnsCorrectDistance() {
    GridPoint point1 = new GridPoint(0, 0);
    GridPoint point2 = new GridPoint(3, 4);

    double expectedDistance = 5.0; // sqrt(3^2 + 4^2) = 5

    double actualDistance = point1.euclideanDistance(point2);

    assertEquals(expectedDistance, actualDistance);
  }

  @Test
  void moveByGivenDirection_pointAndDirection_returnsMovedPoint() {
    Direction2D direction = Direction2D.DOWN_RIGHT;
    GridPoint expectedPoint = new GridPoint(6, 6);

    GridPoint actualPoint = point.moveByGivenDirection(direction);

    assertEquals(expectedPoint, actualPoint);
  }

  @Test
  void scale_pointAndScaleFactor_returnsScaledPoint() {
    GridPoint originalPoint = new GridPoint(2, 3);
    double scaleFactor = 2.0;
    GridPoint expectedPoint = new GridPoint(4, 6);

    GridPoint actualPoint = originalPoint.scale(scaleFactor);

    assertEquals(expectedPoint, actualPoint);
  }

  @Test
  void toString_point_returnsCorrectString() {
    String expectedString = "(5, 5)";

    String actualString = point.toString();

    assertEquals(expectedString, actualString);
  }

}
