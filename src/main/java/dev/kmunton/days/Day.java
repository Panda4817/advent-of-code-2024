package dev.kmunton.days;

import java.util.List;

public interface Day<T, R> {

  default void processData(List<String> data) {
    // Do nothing with data
  }

  T part1();

  R part2();
}
