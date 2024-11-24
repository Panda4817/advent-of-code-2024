package dev.kmunton.utils.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

class CubePointTest {

  @Test
  void getCardinalNeighbors_point_returnsCorrectNeighbors() {
    CubePoint point = new CubePoint(1, 1, 1);
    List<CubePoint> expectedNeighbors = List.of(
        new CubePoint(1, 2, 1),  // UP
        new CubePoint(1, 0, 1),  // DOWN
        new CubePoint(0, 1, 1),  // LEFT
        new CubePoint(2, 1, 1),  // RIGHT
        new CubePoint(1, 1, 2),  // FRONT
        new CubePoint(1, 1, 0)   // BACK
    );

    List<CubePoint> actualNeighbors = point.getCardinalNeighbors();

    assertEquals(expectedNeighbors.size(), actualNeighbors.size());
    assertTrue(actualNeighbors.containsAll(expectedNeighbors));
  }

  @Test
  void getAllNeighbors_point_returnsAllNeighbors() {
    CubePoint point = new CubePoint(0, 0, 0);
    List<CubePoint> expectedNeighbors = new ArrayList<>();
    for (Direction3D dir : Direction3D.values()) {
      expectedNeighbors.add(point.moveByGivenDirection(dir));
    }

    List<CubePoint> actualNeighbors = point.getAllNeighbors();

    assertEquals(expectedNeighbors.size(), actualNeighbors.size());
    assertTrue(actualNeighbors.containsAll(expectedNeighbors));
  }

  @Test
  void getNeighborGivenDirection_pointAndDirection_returnsCorrectNeighbor() {
    CubePoint point = new CubePoint(3, 3, 3);
    Direction3D direction = Direction3D.FRONT;
    CubePoint expectedNeighbor = new CubePoint(3, 3, 4);

    CubePoint actualNeighbor = point.getNeighborGivenDirection(direction);

    assertEquals(expectedNeighbor, actualNeighbor);
  }

  @Test
  void isInBounds_pointInsideBounds_returnsTrue() {
    CubePoint point = new CubePoint(5, 5, 5);
    assertTrue(point.isInBounds(0, 10, 0, 10, 0, 10));
  }

  @Test
  void isInBounds_pointOutsideBounds_returnsFalse() {
    CubePoint point = new CubePoint(11, 5, 5);
    assertFalse(point.isInBounds(0, 10, 0, 10, 0, 10));
  }

  @Test
  void isEdge_pointOnEdge_returnsTrue() {
    CubePoint point = new CubePoint(0, 5, 5);
    assertTrue(point.isEdge(0, 10, 0, 10, 0, 10));
  }

  @Test
  void isEdge_pointNotOnEdge_returnsFalse() {
    CubePoint point = new CubePoint(5, 5, 5);
    assertFalse(point.isEdge(0, 10, 0, 10, 0, 10));
  }

  @Test
  void canMoveGivenDirection_possibleDirection_returnsTrue() {
    CubePoint point = new CubePoint(5, 5, 5);
    Direction3D direction = Direction3D.UP;
    assertTrue(point.canMoveGivenDirection(direction, 0, 10, 0, 10, 0, 10));
  }

  @Test
  void canMoveGivenDirection_impossibleDirection_returnsFalse() {
    CubePoint point = new CubePoint(10, 10, 10);
    Direction3D direction = Direction3D.RIGHT;
    assertFalse(point.canMoveGivenDirection(direction, 0, 10, 0, 10, 0, 10));
  }

  @Test
  void manhattanDistance_twoPoints_returnsCorrectDistance() {
    CubePoint point1 = new CubePoint(1, 2, 3);
    CubePoint point2 = new CubePoint(4, 6, 5);

    int expectedDistance = 9; // |1 - 4| + |2 - 6| + |3 - 5| = 3 + 4 + 2 = 9

    int actualDistance = point1.manhattanDistance(point2);

    assertEquals(expectedDistance, actualDistance);
  }

  @Test
  void euclideanDistance_twoPoints_returnsCorrectDistance() {
    CubePoint point1 = new CubePoint(0, 0, 0);
    CubePoint point2 = new CubePoint(1, 2, 2);

    double expectedDistance = Math.sqrt(9); // sqrt(1^2 + 2^2 + 2^2) = 3

    double actualDistance = point1.euclideanDistance(point2);

    assertEquals(expectedDistance, actualDistance);
  }

  @Test
  void moveByGivenDirection_pointAndDirection_returnsMovedPoint() {
    CubePoint point = new CubePoint(2, 2, 2);
    Direction3D direction = Direction3D.BACK;
    CubePoint expectedPoint = new CubePoint(2, 2, 1);

    CubePoint actualPoint = point.moveByGivenDirection(direction);

    assertEquals(expectedPoint, actualPoint);
  }

  @Test
  void scale_pointAndScaleFactor_returnsScaledPoint() {
    CubePoint point = new CubePoint(1, 2, 3);
    double scaleFactor = 2.0;
    CubePoint expectedPoint = new CubePoint(2, 4, 6);

    CubePoint actualPoint = point.scale(scaleFactor);

    assertEquals(expectedPoint, actualPoint);
  }

  @Test
  void toString_point_returnsCorrectString() {
    CubePoint point = new CubePoint(1, 2, 3);
    String expectedString = "(1, 2, 3)";

    String actualString = point.toString();

    assertEquals(expectedString, actualString);
  }
}

