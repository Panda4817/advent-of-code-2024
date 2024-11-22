package dev.kmunton.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DefaultDayTest {

    private final DefaultDay day = new DefaultDay();

    @Test
    void part1_success() {
        assertEquals(-1, day.part1());
    }

    @Test
    void part2_success() {
        assertEquals(-1, day.part2());
    }
}
