package dev.kmunton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.kmunton.days.Day;
import dev.kmunton.days.DefaultDay;
import dev.kmunton.days.day1.Day1;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RunnerTest {

    private static final LogCaptor LOG_CAPTOR_RUNNER = LogCaptor.forClass(Runner.class);
    private static final LogCaptor LOG_CAPTOR_DEFAULT_DAY = LogCaptor.forClass(DefaultDay.class);

    @BeforeEach
    void setUp() {
        LOG_CAPTOR_RUNNER.clearLogs();
        LOG_CAPTOR_DEFAULT_DAY.clearLogs();
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "", "-1", "26"})
    void mapDayToClass_incorrectInput_throwDayException(String input) {
        Day<Long, Long> obj = Runner.mapDayToClass(input);

        assertEquals(DefaultDay.class, obj.getClass());
    }

    @Test
    void mapDayToClass_correctInput_returnDay() {
        Day<Long, Long> obj = Runner.mapDayToClass("1");

        assertEquals(Day1.class, obj.getClass());
    }

    @Test
    void main_emptyArgs_returnMsg() throws IOException {
        String[] list = {};
        Runner.main(list);

        assertTrue(LOG_CAPTOR_RUNNER.getErrorLogs().contains("Provide day number"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "26", "hello"})
    void main_incorrectDayNumber_printOutErrorMsg(String input) throws IOException {
        String[] list = {input};
        Runner.main(list);

      assertEquals(2, LOG_CAPTOR_DEFAULT_DAY
          .getErrorLogs().stream().filter(m -> m.contains("Incorrect day entered, no result")).toList().size());
      assertTrue(LOG_CAPTOR_RUNNER.getInfoLogs().contains("Part 1: -1"));
      assertTrue(LOG_CAPTOR_RUNNER.getInfoLogs().contains("Part 2: -1"));
    }

    @Test
    void main_tooManyArgs_returnErrorMsg() throws IOException {
        String[] list = {"1", "2"};
        Runner.main(list);

        assertTrue(LOG_CAPTOR_RUNNER.getErrorLogs().contains("Too many days provided. Provide one day."));
    }

    @Test
    void main_correctDayButNoInput_errorMsgNotReturned() throws IOException {
        String[] list = {"1"};
        Runner.main(list);

        assertFalse(LOG_CAPTOR_DEFAULT_DAY
            .getErrorLogs().contains("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result"));
    }

    @Test
    void loadData_correctFilename_returnList() throws FileNotFoundException {
        String resource = "1.txt";
        var data = Runner.loadData(resource, getClass());

        assertEquals(List.of("1   1", "2   2", "3   3", "4   4", "5   5"), data);
    }

    @Test
    void loadData_incorrectFilename_throwException() {
        String resource = "test.txt";

        assertThrows(FileNotFoundException.class, () -> Runner.loadData(resource, getClass()));
    }
}
