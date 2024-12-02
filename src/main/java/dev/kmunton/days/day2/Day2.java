package dev.kmunton.days.day2;

import static java.lang.Math.abs;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.StreamUtils;

@Slf4j
public class Day2 implements Day<Long, Long> {

  private final List<List<Long>> reports = new ArrayList<>();

  public Day2(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(s -> reports.add(Arrays.stream(s.split(" ")).map(Long::parseLong).toList()));
  }

  @Override
  public Long part1() {
    return reports.stream().filter(this::isSafe).count();
  }

  @Override
  public Long part2() {
    List<List<Long>> unsafeReports = reports.stream().filter(r -> !isSafe(r)).toList();

    return unsafeReports.stream().filter(r -> {
      for (int i = 0; i < r.size(); i++) {
        List<Long> copy = new ArrayList<>(r);
        copy.remove(i);
        if (isSafe(copy)) {
          return true;
        }
      }
      return false;
    }).count() + (reports.size() - unsafeReports.size());
  }

  private boolean isSafe(List<Long> report) {
    List<Integer> diffs = StreamUtils.zip(
        report.subList(0, report.size() - 1).stream(),
        report.subList(1, report.size()).stream(),
        (l1, l2) -> {
          long diff = abs(l1 - l2);
          if (diff < 1 || diff > 3) {
            return 0; // Level difference is not 1, 2 or 3
          }
          if (l1 > l2) {
            return -1; // Levels are decreasing
          }
          return 1; // Levels are increasing
        }
    ).toList();

    return diffs.stream().allMatch(l -> l == 1L) // All increasing
        || diffs.stream().allMatch(l -> l == -1L); // All decreasing

  }
}
