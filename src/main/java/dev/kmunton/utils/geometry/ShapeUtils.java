package dev.kmunton.utils.geometry;

public interface ShapeUtils<S, D> {

  S rotateGivenDirection(S shape, int degrees, D direction);

  int maxX(S shape);
  int maxY(S shape);
  int maxZ(S shape);
  int minX(S shape);
  int minY(S shape);
  int minZ(S shape);
}

