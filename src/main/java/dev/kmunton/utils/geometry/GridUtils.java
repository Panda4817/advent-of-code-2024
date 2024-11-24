package dev.kmunton.utils.geometry;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GridUtils implements ShapeUtils<Map<GridPoint, String>, Direction2D> {

  @Override
  public Map<GridPoint, String> rotateGivenDirection(Map<GridPoint, String> grid, int degrees, Direction2D direction) {
    if (degrees % 90 != 0) {
      throw new IllegalArgumentException("Rotation degrees must be a multiple of 90.");
    }

    if (grid.isEmpty()) {
      return grid;
    }

    degrees = ((degrees % 360) + 360) % 360;
    return switch (degrees) {
      case 0 -> grid; // No rotation
      case 90 -> rotate90(grid);
      case 180 -> rotate90(rotate90(grid));
      case 270 -> rotate90(rotate90(rotate90(grid)));
      default -> throw new IllegalStateException("Unexpected degree value: " + degrees);
    };
  }

  @Override
  public int maxX(Map<GridPoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::x)
                .max()
                .orElse(0);
  }

  @Override
  public int maxY(Map<GridPoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::y)
                .max()
                .orElse(0);
  }

  @Override
  public int maxZ(Map<GridPoint, String> shape) {
    // Since GridPoint is 2D, we return 0 or throw an exception if appropriate
    return 0;
  }

  @Override
  public int minX(Map<GridPoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::x)
                .min()
                .orElse(0);
  }

  @Override
  public int minY(Map<GridPoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(GridPoint::y)
                .min()
                .orElse(0);
  }

  @Override
  public int minZ(Map<GridPoint, String> shape) {
    // Since GridPoint is 2D, we return 0 or throw an exception if appropriate
    return 0;
  }


  private Map<GridPoint, String> rotate90(Map<GridPoint, String> grid) {
    Map<GridPoint, String> rotated = new HashMap<>();
    int maxX = maxX(grid);
    for (Map.Entry<GridPoint, String> entry : grid.entrySet()) {
      GridPoint original = entry.getKey();
      GridPoint rotatedPoint = new GridPoint(original.y(), maxX - original.x());
      rotated.put(rotatedPoint, entry.getValue());
    }
    return rotated;
  }
}


