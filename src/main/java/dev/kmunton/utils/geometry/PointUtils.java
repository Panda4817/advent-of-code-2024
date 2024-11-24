package dev.kmunton.utils.geometry;

import java.util.List;

public interface PointUtils<T, D> {

  List<T> getCardinalNeighbors();

  List<T> getAllNeighbors();

  T getNeighborGivenDirection(D direction);

  boolean isInBounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ);

  boolean isEdge(int minX, int maxX, int minY, int maxY, int minZ, int maxZ);

  boolean canMoveGivenDirection(D direction, int minX, int maxX, int minY, int maxY, int minZ, int maxZ);

  int manhattanDistance(T point2);

  double euclideanDistance(T point2);

  T moveByGivenDirection(D direction);

  T scale(double scaleFactor);

}
