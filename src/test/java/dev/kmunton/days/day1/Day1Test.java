package dev.kmunton.days.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

    private static Day1 day1;

    @Test
    void part1() {
        List<String> input = List.of();
        day1 =  new Day1(input);
        assertEquals(-1L, day1.part1());
    }

    @Test
    void part2() {
        List<String> input = List.of();
        day1 =  new Day1(input);
        assertEquals(-1L, day1.part2());
    }
}
