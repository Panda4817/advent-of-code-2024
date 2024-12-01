package dev.kmunton.days.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

    private static Day1 day1;

    @Test
    void part1_success() {
        List<String> input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3""".lines().toList();
        day1 =  new Day1(input);
        assertEquals(11L, day1.part1());
    }

    @Test
    void part2_success() {
        List<String> input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3""".lines().toList();
        day1 =  new Day1(input);
        assertEquals(31L, day1.part2());
    }
}
