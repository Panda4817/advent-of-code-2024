package dev.kmunton.utils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Point2D {

    private int row;
    private int col;

    public Point2D(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point2D point = (Point2D) o;
        return row == point.row && col == point.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col + "]";
    }

    public List<Point2D> getAllEightAdjacentPoints() {
        return List.of(
            new Point2D(row - 1, col),
            new Point2D(row + 1, col),
            new Point2D(row, col - 1),
            new Point2D(row, col + 1),
            new Point2D(row - 1, col - 1),
            new Point2D(row - 1, col + 1),
            new Point2D(row + 1, col - 1),
            new Point2D(row + 1, col + 1)
        );
    }

    public Point2D getAdjacentPoint(Direction direction) {
        return switch (direction) {
            case UP -> new Point2D(row - 1, col);
            case DOWN -> new Point2D(row + 1, col);
            case LEFT -> new Point2D(row, col - 1);
            case RIGHT -> new Point2D(row, col + 1);
            case UP_LEFT -> new Point2D(row - 1, col - 1);
            case UP_RIGHT -> new Point2D(row - 1, col + 1);
            case DOWN_LEFT -> new Point2D(row + 1, col - 1);
            case DOWN_RIGHT -> new Point2D(row + 1, col + 1);
        };
    }

    public long calculateManhattanDistance(Point2D other) {
        return (long) Math.abs(this.row - other.getRow()) + Math.abs(this.col - other.getCol());
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP -> this.row--;
            case DOWN -> this.row++;
            case LEFT -> this.col--;
            case RIGHT -> this.col++;
            case UP_LEFT -> {
                this.row--;
                this.col--;
            }
            case UP_RIGHT -> {
                this.row--;
                this.col++;
            }
            case DOWN_LEFT -> {
                this.row++;
                this.col--;
            }
            case DOWN_RIGHT -> {
                this.row++;
                this.col++;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    @SafeVarargs
    public final boolean canMoveUp(Set<Point2D>... blockers) {
        if (this.getRow() == 0) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point2D(this.getRow() - 1, this.getCol()))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean canMoveDown(int maxRows, Set<Point2D>... blockers) {
        if (this.getRow() == maxRows - 1) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point2D(this.getRow() + 1, this.getCol()))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean canMoveLeft(Set<Point2D>... blockers) {
        if (this.getCol() == 0) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point2D(this.getRow(), this.getCol() - 1))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean canMoveRight(int maxCols, Set<Point2D>... blockers) {
        if (this.getCol() == maxCols - 1) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point2D(this.getRow(), this.getCol() + 1))) {
                return false;
            }
        }
        return true;
    }

    public boolean isOnGrid(int maxRows, int maxCols) {
        return this.getRow() >= 0 && this.getRow() < maxRows && this.getCol() >= 0 && this.getCol() < maxCols;
    }

    public boolean atEdge(int maxRows, int maxCols) {
        return this.getRow() == 0 || this.getRow() == maxRows - 1 || this.getCol() == 0 || this.getCol() == maxCols - 1;
    }
}
