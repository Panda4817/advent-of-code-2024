package dev.kmunton.utils.geometry;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CubeUtils implements ShapeUtils<Map<CubePoint, String>, Direction3D> {


  @Override
  public Map<CubePoint, String> rotateGivenDirection(Map<CubePoint, String> shape, int degrees, Direction3D direction) {
    return Map.of(); // Not implemented yet
  }

  @Override
  public int maxX(Map<CubePoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(CubePoint::x)
                .max()
                .orElse(0);
  }

  @Override
  public int maxY(Map<CubePoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(CubePoint::y)
                .max()
                .orElse(0);
  }

  @Override
  public int maxZ(Map<CubePoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(CubePoint::z)
                .max()
                .orElse(0);
  }

  @Override
  public int minX(Map<CubePoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(CubePoint::x)
                .min()
                .orElse(0);
  }

  @Override
  public int minY(Map<CubePoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(CubePoint::y)
                .min()
                .orElse(0);
  }

  @Override
  public int minZ(Map<CubePoint, String> shape) {
    return shape.keySet().stream()
                .mapToInt(CubePoint::z)
                .min()
                .orElse(0);
  }
}


