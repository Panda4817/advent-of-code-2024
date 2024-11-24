package dev.kmunton.utils.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class GridUtilsTest {

  private GridUtils gridUtils;

  @BeforeEach
  void setUp() {
    gridUtils = new GridUtils();
  }

  @Test
  void rotateGivenDirection_noRotation_returnsSameGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, 0, Direction2D.RIGHT);

    assertEquals(grid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_rotate90Degrees_returnsRotatedGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, 90, Direction2D.RIGHT);

    Map<GridPoint, String> expectedGrid = new HashMap<>();
    expectedGrid.put(new GridPoint(0, 1), "A");
    expectedGrid.put(new GridPoint(0, 0), "B");
    expectedGrid.put(new GridPoint(1, 1), "C");
    expectedGrid.put(new GridPoint(1, 0), "D");

    assertEquals(expectedGrid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_rotate180Degrees_returnsRotatedGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, 180, Direction2D.RIGHT);

    Map<GridPoint, String> expectedGrid = new HashMap<>();
    expectedGrid.put(new GridPoint(1, 1), "A");
    expectedGrid.put(new GridPoint(0, 1), "B");
    expectedGrid.put(new GridPoint(1, 0), "C");
    expectedGrid.put(new GridPoint(0, 0), "D");

    assertEquals(expectedGrid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_rotate270Degrees_returnsRotatedGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, 270, Direction2D.RIGHT);

    Map<GridPoint, String> expectedGrid = new HashMap<>();
    expectedGrid.put(new GridPoint(1, 0), "A");
    expectedGrid.put(new GridPoint(1, 1), "B");
    expectedGrid.put(new GridPoint(0, 0), "C");
    expectedGrid.put(new GridPoint(0, 1), "D");

    assertEquals(expectedGrid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_rotateNegative90Degrees_returnsRotatedGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, -90, Direction2D.RIGHT);

    // Rotating by -90 degrees should be equivalent to rotating by 270 degrees
    Map<GridPoint, String> expectedGrid = new HashMap<>();
    expectedGrid.put(new GridPoint(1, 0), "A");
    expectedGrid.put(new GridPoint(1, 1), "B");
    expectedGrid.put(new GridPoint(0, 0), "C");
    expectedGrid.put(new GridPoint(0, 1), "D");

    assertEquals(expectedGrid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_rotateNegative180Degrees_returnsRotatedGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, -180, Direction2D.RIGHT);

    // Rotating by -180 degrees should be equivalent to rotating by 180 degrees
    Map<GridPoint, String> expectedGrid = new HashMap<>();
    expectedGrid.put(new GridPoint(1, 1), "A");
    expectedGrid.put(new GridPoint(0, 1), "B");
    expectedGrid.put(new GridPoint(1, 0), "C");
    expectedGrid.put(new GridPoint(0, 0), "D");

    assertEquals(expectedGrid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_rotateNegative270Degrees_returnsRotatedGrid() {
    Map<GridPoint, String> grid = createSampleGrid();
    Map<GridPoint, String> rotatedGrid = gridUtils.rotateGivenDirection(grid, -270, Direction2D.RIGHT);

    // Rotating by -270 degrees should be equivalent to rotating by 90 degrees
    Map<GridPoint, String> expectedGrid = new HashMap<>();
    expectedGrid.put(new GridPoint(0, 1), "A");
    expectedGrid.put(new GridPoint(0, 0), "B");
    expectedGrid.put(new GridPoint(1, 1), "C");
    expectedGrid.put(new GridPoint(1, 0), "D");

    assertEquals(expectedGrid, rotatedGrid);
  }

  @Test
  void rotateGivenDirection_invalidDegrees_throwsException() {
    Map<GridPoint, String> grid = createSampleGrid();
    assertThrows(IllegalArgumentException.class, () -> {
      gridUtils.rotateGivenDirection(grid, 45, Direction2D.RIGHT);
    });
  }

  @Test
  void maxX_shape_returnsMaxXValue() {
    Map<GridPoint, String> shape = new HashMap<>();
    shape.put(new GridPoint(1, 2), "A");
    shape.put(new GridPoint(3, 5), "B");
    shape.put(new GridPoint(-2, 0), "C");

    int mX = gridUtils.maxX(shape);
    assertEquals(3, mX);
  }

  @Test
  void maxY_shape_returnsMaxYValue() {
    Map<GridPoint, String> shape = new HashMap<>();
    shape.put(new GridPoint(1, 2), "A");
    shape.put(new GridPoint(3, 5), "B");
    shape.put(new GridPoint(-2, 0), "C");

    int mY = gridUtils.maxY(shape);
    assertEquals(5, mY);
  }

  @Test
  void minX_shape_returnsMinXValue() {
    Map<GridPoint, String> shape = new HashMap<>();
    shape.put(new GridPoint(1, 2), "A");
    shape.put(new GridPoint(3, 5), "B");
    shape.put(new GridPoint(-2, 0), "C");

    int mX = gridUtils.minX(shape);
    assertEquals(-2, mX);
  }

  @Test
  void minY_shape_returnsMinYValue() {
    Map<GridPoint, String> shape = new HashMap<>();
    shape.put(new GridPoint(1, 2), "A");
    shape.put(new GridPoint(3, 5), "B");
    shape.put(new GridPoint(-2, 0), "C");

    int mY = gridUtils.minY(shape);
    assertEquals(0, mY);
  }

  @Test
  void maxZMinZ_shape_returnsZero() {
    Map<GridPoint, String> shape = new HashMap<>();
    shape.put(new GridPoint(1, 2), "A");

    int maZ = gridUtils.maxZ(shape);
    int miZ = gridUtils.minZ(shape);

    assertEquals(0, maZ);
    assertEquals(0, miZ);
  }

  // Helper method to create a sample grid
  private Map<GridPoint, String> createSampleGrid() {
    Map<GridPoint, String> grid = new HashMap<>();
    grid.put(new GridPoint(0, 0), "A");
    grid.put(new GridPoint(1, 0), "B");
    grid.put(new GridPoint(0, 1), "C");
    grid.put(new GridPoint(1, 1), "D");
    return grid;
  }
}

