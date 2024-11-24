package dev.kmunton.utils.geometry;

import java.util.Arrays;
import java.util.List;

public record GridPoint(int x, int y) implements PointUtils<GridPoint, Direction2D> {

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public List<GridPoint> getCardinalNeighbors() {
    return Arrays.asList(
        new GridPoint(this.x + Direction2D.UP.getDx(), this.y + Direction2D.UP.getDy()),
        new GridPoint(this.x + Direction2D.DOWN.getDx(), this.y + Direction2D.DOWN.getDy()),
        new GridPoint(this.x + Direction2D.LEFT.getDx(), this.y + Direction2D.LEFT.getDy()),
        new GridPoint(this.x + Direction2D.RIGHT.getDx(), this.y + Direction2D.RIGHT.getDy())
    );
  }

  @Override
  public List<GridPoint> getAllNeighbors() {
    return Arrays.asList(
        new GridPoint(this.x + Direction2D.UP.getDx(), this.y + Direction2D.UP.getDy()),
        new GridPoint(this.x + Direction2D.DOWN.getDx(), this.y + Direction2D.DOWN.getDy()),
        new GridPoint(this.x + Direction2D.LEFT.getDx(), this.y + Direction2D.LEFT.getDy()),
        new GridPoint(this.x + Direction2D.RIGHT.getDx(), this.y + Direction2D.RIGHT.getDy()),
        new GridPoint(this.x + Direction2D.UP_LEFT.getDx(), this.y + Direction2D.UP_LEFT.getDy()),
        new GridPoint(this.x + Direction2D.UP_RIGHT.getDx(), this.y + Direction2D.UP_RIGHT.getDy()),
        new GridPoint(this.x + Direction2D.DOWN_LEFT.getDx(), this.y + Direction2D.DOWN_LEFT.getDy()),
        new GridPoint(this.x + Direction2D.DOWN_RIGHT.getDx(), this.y + Direction2D.DOWN_RIGHT.getDy())
    );
  }

  @Override
  public GridPoint getNeighborGivenDirection(Direction2D direction) {
    return moveByGivenDirection(direction);
  }

  @Override
  public boolean isInBounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    return this.x >= minX && this.x <= maxX && this.y >= minY && this.y <= maxY;
  }

  @Override
  public boolean isEdge(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    return this.x == minX || this.x == maxX || this.y == minY || this.y == maxY;
  }

  @Override
  public boolean canMoveGivenDirection(Direction2D direction, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    GridPoint neighbor = moveByGivenDirection(direction);
    return neighbor.isInBounds(minX, maxX, minY, maxY, minZ, maxZ);
  }

  @Override
  public int manhattanDistance(GridPoint point2) {
    return Math.abs(this.x - point2.x) + Math.abs(this.y - point2.y);
  }

  @Override
  public double euclideanDistance(GridPoint point2) {
    return Math.sqrt(Math.pow((double) this.x - point2.x, 2) + Math.pow((double) this.y - point2.y, 2));
  }

  @Override
  public GridPoint moveByGivenDirection(Direction2D direction) {
    return new GridPoint(this.x + direction.getDx(), this.y + direction.getDy());
  }

  @Override
  public GridPoint scale(double scaleFactor) {
    return new GridPoint((int) (this.x * scaleFactor), (int) (this.y * scaleFactor));
  }
}

