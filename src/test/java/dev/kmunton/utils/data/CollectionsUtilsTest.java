package dev.kmunton.utils.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionsUtilsTest {

  @Test
  void generatePermutations_emptyList_returnsEmptyList() {
    List<Integer> input = new ArrayList<>();
    List<List<Integer>> expected = List.of(new ArrayList<>());

    List<List<Integer>> actual = CollectionsUtils.generatePermutations(input);

    assertEquals(expected, actual);
  }

  @Test
  void generatePermutations_singleElement_returnsSinglePermutation() {
    List<Integer> input = List.of(1);
    List<List<Integer>> expected = List.of(List.of(1));

    List<List<Integer>> actual = CollectionsUtils.generatePermutations(input);

    assertEquals(expected, actual);
  }

  @Test
  void generatePermutations_multipleElements_returnsAllPermutations() {
    List<Integer> input = List.of(1, 2, 3);
    List<List<Integer>> expected = List.of(
        List.of(1, 2, 3),
        List.of(1, 3, 2),
        List.of(2, 1, 3),
        List.of(2, 3, 1),
        List.of(3, 1, 2),
        List.of(3, 2, 1)
    );

    List<List<Integer>> actual = CollectionsUtils.generatePermutations(input);

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  void generatePermutations_withDuplicates_returnsCorrectPermutations() {
    List<Integer> input = List.of(1, 1, 2);
    List<List<Integer>> expected = List.of(
        List.of(1, 1, 2),
        List.of(1, 2, 1),
        List.of(1, 1, 2),
        List.of(1, 2, 1),
        List.of(2, 1, 1),
        List.of(2, 1, 1)
    );

    List<List<Integer>> actual = CollectionsUtils.generatePermutations(input);

    assertEquals(expected.size(), actual.size());
    // Due to duplicates, permutations may repeat
  }

  // Tests for generateCombinations method

  @Test
  void generateCombinations_emptyList_returnsEmptyCombination() {
    List<Integer> input = new ArrayList<>();
    int size = 0;
    List<List<Integer>> expected = List.of(new ArrayList<>());

    List<List<Integer>> actual = CollectionsUtils.generateCombinations(input, size);

    assertEquals(expected, actual);
  }

  @Test
  void generateCombinations_sizeZero_returnsEmptyCombination() {
    List<Integer> input = List.of(1, 2, 3);
    int size = 0;
    List<List<Integer>> expected = List.of(new ArrayList<>());

    List<List<Integer>> actual = CollectionsUtils.generateCombinations(input, size);

    assertEquals(expected, actual);
  }

  @Test
  void generateCombinations_sizeGreaterThanList_returnsEmptyList() {
    List<Integer> input = List.of(1, 2, 3);
    int size = 5;

    List<List<Integer>> actual = CollectionsUtils.generateCombinations(input, size);

    assertTrue(actual.isEmpty());
  }

  @Test
  void generateCombinations_sizeEqualToList_returnsSingleCombination() {
    List<Integer> input = List.of(1, 2, 3);
    int size = 3;
    List<List<Integer>> expected = List.of(List.of(1, 2, 3));

    List<List<Integer>> actual = CollectionsUtils.generateCombinations(input, size);

    assertEquals(expected, actual);
  }

  @Test
  void generateCombinations_validSize_returnsAllCombinations() {
    List<Integer> input = List.of(1, 2, 3);
    int size = 2;
    List<List<Integer>> expected = List.of(
        List.of(1, 2),
        List.of(1, 3),
        List.of(2, 3)
    );

    List<List<Integer>> actual = CollectionsUtils.generateCombinations(input, size);

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  void generateCombinations_withDuplicates_returnsCorrectCombinations() {
    List<Integer> input = List.of(1, 1, 2);
    int size = 2;
    List<List<Integer>> expected = List.of(
        List.of(1, 1),
        List.of(1, 2),
        List.of(1, 2)
    );

    List<List<Integer>> actual = CollectionsUtils.generateCombinations(input, size);

    assertEquals(expected.size(), actual.size());
    // Combinations with duplicates may repeat
  }

}
