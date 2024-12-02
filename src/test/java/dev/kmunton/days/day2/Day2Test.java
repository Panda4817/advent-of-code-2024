package dev.kmunton.days.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day2Test {

  private static Day2 day;

  @Test
  void part1_success() {
    List<String> input = """
             7 6 4 2 1
             1 2 7 8 9
             9 7 6 2 1
             1 3 2 4 5
             8 6 4 4 1
             1 3 6 7 9""".lines().toList();
    day =  new Day2(input);
    assertEquals(2L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
             7 6 4 2 1
             1 2 7 8 9
             9 7 6 2 1
             1 3 2 4 5
             8 6 4 4 1
             1 3 6 7 9""".lines().toList();
    day =  new Day2(input);
    assertEquals(4L, day.part2());
  }
}
