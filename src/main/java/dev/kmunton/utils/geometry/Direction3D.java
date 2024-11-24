package dev.kmunton.utils.geometry;

import lombok.Getter;

@Getter
public enum Direction3D implements Direction {
  UP(0, 1, 0), DOWN(0, -1, 0),
  LEFT(-1, 0, 0), RIGHT(1, 0, 0),
  FRONT(0, 0, 1), BACK(0, 0, -1);

  private final int dx;
  private final int dy;
  private final int dz;

  Direction3D(int dx, int dy, int dz) {
    this.dx = dx;
    this.dy = dy;
    this.dz = dz;
  }

}

