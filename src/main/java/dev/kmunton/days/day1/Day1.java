package dev.kmunton.days.day1;

import static java.lang.Math.abs;
import static org.springframework.data.util.StreamUtils.zip;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day1 implements Day<Long, Long> {

  private final List<Long> listL = new ArrayList<>();
  private final List<Long> listR = new ArrayList<>();

  public Day1(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(s -> {
      String[] split = s.split(" {3}");
      listL.add(Long.parseLong(split[0]));
      listR.add(Long.parseLong(split[1]));
    });
  }

  @Override
  public Long part1() {
    listL.sort(Long::compareTo);
    listR.sort(Long::compareTo);
    return zip(
        listL.stream(),
        listR.stream(),
        (l, r) -> abs(l - r)
    ).reduce(0L, Long::sum);
  }

  @Override
  public Long part2() {
    return listL.stream()
                .map(l -> (l * (listR.stream().filter(r -> Objects.equals(r, l)).count())))
                .reduce(0L, Long::sum);
  }

}
