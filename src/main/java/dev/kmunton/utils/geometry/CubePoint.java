package dev.kmunton.utils.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record CubePoint(int x, int y, int z) implements PointUtils<CubePoint, Direction3D> {

  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }

  @Override
  public List<CubePoint> getCardinalNeighbors() {
    return Arrays.asList(
        moveByGivenDirection(Direction3D.UP),
        moveByGivenDirection(Direction3D.DOWN),
        moveByGivenDirection(Direction3D.LEFT),
        moveByGivenDirection(Direction3D.RIGHT),
        moveByGivenDirection(Direction3D.FRONT),
        moveByGivenDirection(Direction3D.BACK)
    );
  }

  @Override
  public List<CubePoint> getAllNeighbors() {
    List<CubePoint> neighbors = new ArrayList<>();
    for (Direction3D dir : Direction3D.values()) {
      neighbors.add(moveByGivenDirection(dir));
    }
    return neighbors;
  }

  @Override
  public CubePoint getNeighborGivenDirection(Direction3D direction) {
    return moveByGivenDirection(direction);
  }

  @Override
  public boolean isInBounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    return this.x >= minX && this.x <= maxX &&
        this.y >= minY && this.y <= maxY &&
        this.z >= minZ && this.z <= maxZ;
  }

  @Override
  public boolean isEdge(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    return this.x == minX || this.x == maxX ||
        this.y == minY || this.y == maxY ||
        this.z == minZ || this.z == maxZ;
  }

  @Override
  public boolean canMoveGivenDirection(Direction3D direction, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    CubePoint neighbor = moveByGivenDirection(direction);
    return neighbor.isInBounds(minX, maxX, minY, maxY, minZ, maxZ);
  }

  @Override
  public int manhattanDistance(CubePoint point2) {
    return Math.abs(this.x - point2.x) +
        Math.abs(this.y - point2.y) +
        Math.abs(this.z - point2.z);
  }

  @Override
  public double euclideanDistance(CubePoint point2) {
    return Math.sqrt(
        Math.pow((double) this.x - point2.x, 2) +
            Math.pow((double) this.y - point2.y, 2) +
            Math.pow((double) this.z - point2.z, 2)
    );
  }

  @Override
  public CubePoint moveByGivenDirection(Direction3D direction) {
    return new CubePoint(
        this.x + direction.getDx(),
        this.y + direction.getDy(),
        this.z + direction.getDz()
    );
  }

  @Override
  public CubePoint scale(double scaleFactor) {
    return new CubePoint(
        (int) (this.x * scaleFactor),
        (int) (this.y * scaleFactor),
        (int) (this.z * scaleFactor)
    );
  }
}

