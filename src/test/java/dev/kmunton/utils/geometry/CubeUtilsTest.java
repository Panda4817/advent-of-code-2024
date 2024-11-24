package dev.kmunton.utils.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class CubeUtilsTest {

  private CubeUtils cubeUtils;
  private Map<CubePoint, String> cube;

  @BeforeEach
  void setUp() {
    cubeUtils = new CubeUtils();
    cube = createSampleCube();
  }

  @Test
  void maxX_cube_returnsMaxXValue() {
    int maxX = cubeUtils.maxX(cube);
    assertEquals(1, maxX);
  }

  @Test
  void maxY_cube_returnsMaxYValue() {
    int maxY = cubeUtils.maxY(cube);
    assertEquals(0, maxY);
  }

  @Test
  void maxZ_cube_returnsMaxZValue() {
    int maxZ = cubeUtils.maxZ(cube);
    assertEquals(1, maxZ);
  }

  @Test
  void minX_cube_returnsMinXValue() {
    int minX = cubeUtils.minX(cube);
    assertEquals(0, minX);
  }

  @Test
  void minY_cube_returnsMinYValue() {
    int minY = cubeUtils.minY(cube);
    assertEquals(0, minY);
  }

  @Test
  void minZ_cube_returnsMinZValue() {
    int minZ = cubeUtils.minZ(cube);
    assertEquals(0, minZ);
  }

  @Test
  void rotateGivenDirection_emptyCube_returnsEmptyCube() {
    Map<CubePoint, String> emptyCube = new HashMap<>();
    Map<CubePoint, String> rotatedCube = cubeUtils.rotateGivenDirection(emptyCube, 90, Direction3D.UP);

    assertTrue(rotatedCube.isEmpty());
  }

  // Helper method to create a sample cube
  private Map<CubePoint, String> createSampleCube() {
    Map<CubePoint, String> cubeGrid = new HashMap<>();
    cubeGrid.put(new CubePoint(0, 0, 0), "A");
    cubeGrid.put(new CubePoint(1, 0, 0), "B");
    cubeGrid.put(new CubePoint(0, 0, 1), "C");
    cubeGrid.put(new CubePoint(1, 0, 1), "D");
    return cubeGrid;
  }
}

