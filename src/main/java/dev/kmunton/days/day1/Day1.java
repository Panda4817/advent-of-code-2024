package dev.kmunton.days.day1;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.List;

public class Day1 implements Day<Long, Long> {

  private final List<String> data = new ArrayList<>();

  public Day1(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    this.data.addAll(input);
  }

  @Override
  public Long part1() {
    return -1L;
  }

  @Override
  public Long part2() {
    return -1L;

  }

}
