package dev.kmunton.utils.geometry;

import java.util.List;
import java.util.Optional;

public record Range(long min, long max) implements Comparable<Range> {

  public Range {
    if (min > max) {
      throw new IllegalArgumentException("min cannot be greater than max");
    }
  }

  public boolean contains(long value) {
    return value >= min && value <= max;
  }

  public boolean overlaps(Range other) {
    return this.min <= other.max && other.min <= this.max;
  }

  public boolean isEmpty() {
    return min == max;
  }

  public long length() {
    return max - min;
  }

  public Optional<Range> intersect(Range other) {
    long newMin = Math.max(this.min, other.min);
    long newMax = Math.min(this.max, other.max);
    if (newMin <= newMax) {
      return Optional.of(new Range(newMin, newMax));
    } else {
      return Optional.empty();
    }
  }

  public Range union(Range other) {
    long newMin = Math.min(this.min, other.min);
    long newMax = Math.max(this.max, other.max);
    return new Range(newMin, newMax);
  }

  public Range shift(long amount) {
    return new Range(this.min + amount, this.max + amount);
  }

  public Range expand(long amount) {
    return new Range(this.min - amount, this.max + amount);
  }

  public long clamp(long value) {
    if (value < min) return min;
    if (value > max) return max;
    return value;
  }

  public boolean isSubRangeOf(Range other) {
    return this.min >= other.min && this.max <= other.max;
  }

  @Override
  public int compareTo(Range other) {
    int minComparison = Long.compare(this.min, other.min);
    if (minComparison != 0) {
      return minComparison;
    }
    return Long.compare(this.max, other.max);
  }

  public List<Range> split(long point) {
    if (point <= min || point >= max) {
      return List.of(this);
    } else {
      Range lower = new Range(min, point);
      Range upper = new Range(point, max);
      return List.of(lower, upper);
    }
  }

  @Override
  public String toString() {
    return "Range[" + min + ", " + max + "]";
  }

  public Optional<Range> merge(Range other) {
    if (this.max >= other.min - 1 && other.max >= this.min - 1) {
      long newMin = Math.min(this.min, other.min);
      long newMax = Math.max(this.max, other.max);
      return Optional.of(new Range(newMin, newMax));
    } else {
      return Optional.empty();
    }
  }

  public Optional<Range> gap(Range other) {
    if (this.overlaps(other) || this.adjacentTo(other)) {
      return Optional.empty();
    }
    long newMin = Math.min(this.max, other.max);
    long newMax = Math.max(this.min, other.min);
    return Optional.of(new Range(newMin, newMax));
  }

  private boolean adjacentTo(Range other) {
    return this.max + 1 == other.min || other.max + 1 == this.min;
  }

  public String format(String pattern) {
    return String.format(pattern, min, max);
  }

  public double midpoint() {
    return (min + max) / 2.0;
  }

  public Range scale(double factor) {
    double mid = midpoint();
    double halfLength = length() * factor / 2.0;
    long newMin = (long) Math.floor(mid - halfLength);
    long newMax = (long) Math.ceil(mid + halfLength);
    return new Range(newMin, newMax);
  }
}

