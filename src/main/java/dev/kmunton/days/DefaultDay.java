package dev.kmunton.days;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDay implements Day<Long, Long> {

  private static final Long DEFAULT_VALUE = -1L;

  @Override
  public Long part1() {
    logIncorrectDayMessage();
    return DEFAULT_VALUE;
  }

  @Override
  public Long part2() {
    logIncorrectDayMessage();
    return DEFAULT_VALUE;
  }

  private void logIncorrectDayMessage() {
    log.error("Incorrect day entered, no result");
  }
}
