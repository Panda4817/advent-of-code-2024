package dev.kmunton.utils.data;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionsUtils {

  /**
   * Generates all permutations of a given list.
   *
   * @param list The input list for which permutations are to be generated.
   * @param <T>  The type of the elements in the list.
   * @return A list of all permutations of the input list.
   */
  public static <T> List<List<T>> generatePermutations(List<T> list) {
    List<List<T>> results = new ArrayList<>();
    backtrackPermutations(results, new ArrayList<>(), list, new boolean[list.size()]);
    return results;
  }

  private static <T> void backtrackPermutations(List<List<T>> results, List<T> tempList, List<T> list, boolean[] used) {
    if (tempList.size() == list.size()) {
      results.add(new ArrayList<>(tempList));
    } else {
      for (int i = 0; i < list.size(); i++) {
        if (used[i]) continue; // Skip already used elements
        used[i] = true;
        tempList.add(list.get(i));
        backtrackPermutations(results, tempList, list, used);
        used[i] = false;
        tempList.removeLast();
      }
    }
  }

  /**
   * Generates all combinations of a given size from the input list.
   *
   * @param list The input list for which combinations are to be generated.
   * @param size The size of each combination.
   * @param <T>  The type of the elements in the list.
   * @return A list of all combinations of the given size.
   */
  public static <T> List<List<T>> generateCombinations(List<T> list, int size) {
    List<List<T>> results = new ArrayList<>();
    backtrackCombinations(results, new ArrayList<>(), list, size, 0);
    return results;
  }

  private static <T> void backtrackCombinations(List<List<T>> results, List<T> tempList, List<T> list, int size, int start) {
    if (tempList.size() == size) {
      results.add(new ArrayList<>(tempList));
    } else {
      for (int i = start; i < list.size(); i++) {
        tempList.add(list.get(i));
        backtrackCombinations(results, tempList, list, size, i + 1);
        tempList.removeLast();
      }
    }
  }
}


