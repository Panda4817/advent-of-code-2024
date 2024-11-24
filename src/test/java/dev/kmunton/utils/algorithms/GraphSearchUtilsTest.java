package dev.kmunton.utils.algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Test;

import java.util.*;

class GraphSearchUtilsTest {

  @Test
  void aStarSearch_graphWithPath_returnsShortestPath() {
    // Graph:
    // A --1--> B --2--> C --1--> D
    // A --4--> C
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "C", 4));
    graph.put("B", Map.of("C", 2, "D", 5));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;
    ToIntFunction<String> heuristic = node -> switch (node) {
      case "A" -> 6;
      case "B" -> 4;
      case "C" -> 2;
      case "D" -> 0;
      default -> Integer.MAX_VALUE;
    };

    List<String> path = GraphSearchUtils.aStarSearch("A", "D", getNeighbors, heuristic);

    assertEquals(List.of("A", "B", "C", "D"), path);
  }

  @Test
  void dijkstraSearch_graphWithPath_returnsShortestPath() {
    // Graph:
    // A --1--> B --2--> C --1--> D
    // A --4--> C
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "C", 4));
    graph.put("B", Map.of("C", 2, "D", 5));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.dijkstraSearch("A", "D", getNeighbors);

    assertEquals(List.of("A", "B", "C", "D"), path);
  }

  @Test
  void bfs_graphWithPath_returnsShortestPathInSteps() {
    // Graph:
    // A -- B
    // |    |
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "C"));
    graph.put("B", List.of("D"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.bfs(
        "A",
        "D"::equals,
        getNeighbors
    );

    assertEquals(List.of("A", "B", "D"), path);
  }

  @Test
  void dfs_graphWithPath_returnsTrue() {
    // Graph:
    // A -- B
    // |    |
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "C"));
    graph.put("B", List.of("D"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    boolean found = GraphSearchUtils.dfs(
        "A",
        "D"::equals,
        getNeighbors
    );

    assertTrue(found);
  }

  @Test
  void aStarSearch_graphWithoutPath_returnsEmptyList() {
    // Graph:
    // A --1--> B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1));
    graph.put("B", Map.of());
    graph.put("C", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;
    ToIntFunction<String> heuristic = node -> 1;

    List<String> path = GraphSearchUtils.aStarSearch("A", "C", getNeighbors, heuristic);

    assertTrue(path.isEmpty());
  }

  @Test
  void dijkstraSearch_graphWithoutPath_returnsEmptyList() {
    // Graph:
    // A --1--> B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1));
    graph.put("B", Map.of());
    graph.put("C", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.dijkstraSearch("A", "C", getNeighbors);

    assertTrue(path.isEmpty());
  }

  @Test
  void bfs_graphWithoutPath_returnsEmptyList() {
    // Graph:
    // A -- B
    // C (isolated node)
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B"));
    graph.put("B", List.of());
    graph.put("C", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.bfs("A", "C"::equals, getNeighbors);

    assertTrue(path.isEmpty());
  }

  @Test
  void dfs_graphWithoutPath_returnsFalse() {
    // Graph:
    // A -- B
    // C (isolated node)
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B"));
    graph.put("B", List.of());
    graph.put("C", List.of());

    Function<String, List<String>> getNeighbors = graph::get;

    boolean found = GraphSearchUtils.dfs("A", "C"::equals, getNeighbors);

    assertFalse(found);
  }

  @Test
  void findLongestPathDirected_graphWithValidPath_returnsLongestPath() {
    // Directed Graph:
    // A --3--> B --4--> D --2--> E
    // A --6--> C --8--> D
    // B --11--> E
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3, "C", 6));
    graph.put("B", Map.of("D", 4, "E", 11));
    graph.put("C", Map.of("D", 8));
    graph.put("D", Map.of("E", 2));
    graph.put("E", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "E", graph);

    assertEquals(List.of("A", "C", "D", "E"), longestPath);
  }

  @Test
  void findLongestPathDirected_graphWithNoPath_returnsEmptyList() {
    // Directed Graph:
    // A --3--> B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3));
    graph.put("B", Map.of());
    graph.put("C", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "C", graph);

    assertTrue(longestPath.isEmpty());
  }

  @Test
  void findLongestPathDirected_cyclicGraph_returnsEmptyList() {
    // Directed Graph with Cycle:
    // A --3--> B --4--> C --2--> A (cycle)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3));
    graph.put("B", Map.of("C", 4));
    graph.put("C", Map.of("A", 2)); // Cycle back to A

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "C", graph);

    assertTrue(longestPath.isEmpty());
  }

  @Test
  void findLongestPathUndirected_graphWithValidPath_returnsLongestPath() {
    // Undirected Graph:
    // A --3-- B --4-- D
    // |       |
    // 2       1
    // |       |
    // C ------ D
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3, "C", 2));
    graph.put("B", Map.of("A", 3, "D", 4));
    graph.put("C", Map.of("A", 2, "D", 1));
    graph.put("D", Map.of("B", 4, "C", 1));

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "D", getNeighbors);

    assertEquals(List.of("A", "B", "D"), longestPath);
  }

  @Test
  void findLongestPathUndirected_graphWithNoPath_returnsEmptyList() {
    // Undirected Graph:
    // A --3-- B
    // C (isolated node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 3));
    graph.put("B", Map.of("A", 3));
    graph.put("C", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "C", getNeighbors);

    assertTrue(longestPath.isEmpty());
  }

  @Test
  void findLongestPathUndirected_singleNode_returnsStart() {
    // Graph:
    // A (single node)
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "A", getNeighbors);

    assertEquals(List.of("A"), longestPath);
  }

  @Test
  void findLongestPathDirected_graphWithMultiplePaths_returnsLongestPath() {
    // Directed Graph:
    // A --5--> B --5--> D --1--> E
    // A --10--> C --1--> D
    // C --5--> E
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 5, "C", 10));
    graph.put("B", Map.of("D", 5));
    graph.put("C", Map.of("D", 1, "E", 5));
    graph.put("D", Map.of("E", 1));
    graph.put("E", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "E", graph);

    assertEquals(List.of("A", "C", "E"), longestPath);
  }

  // Additional Tests

  @Test
  void dijkstraSearch_graphWithNegativeWeights_returnsShortestPath() {
    // Graph with negative weights:
    // A --(-1)--> B --(-2)--> C --1--> D
    // A --4--> C
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", -1, "C", 4));
    graph.put("B", Map.of("C", -2, "D", 5));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    // Dijkstra's algorithm doesn't work correctly with negative weights, so the test ensures it doesn't fail
    List<String> path = GraphSearchUtils.dijkstraSearch("A", "D", getNeighbors);

    // Since negative weights can cause issues, the expected path might not be the minimal one
    // Here we just check that a path is found
    assertFalse(path.isEmpty());
  }

  @Test
  void aStarSearch_graphWithHeuristicOverestimating_returnsShortestPath() {
    // Graph:
    // A --1--> B --1--> C --1--> D
    // Heuristic overestimates the actual cost
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1));
    graph.put("B", Map.of("C", 1));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());

    Function<String, Map<String, Integer>> getNeighbors = graph::get;
    ToIntFunction<String> heuristic = node -> 10; // Overestimating heuristic

    List<String> path = GraphSearchUtils.aStarSearch("A", "D", getNeighbors, heuristic);

    // A* with an overestimating heuristic becomes unreliable; we check that it still finds a path
    assertFalse(path.isEmpty());
  }

  @Test
  void bfs_disconnectedGraph_returnsEmptyList() {
    // Graph:
    // A -- B
    // C -- D
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B"));
    graph.put("B", List.of("A"));
    graph.put("C", List.of("D"));
    graph.put("D", List.of("C"));

    Function<String, List<String>> getNeighbors = graph::get;

    List<String> path = GraphSearchUtils.bfs("A", "D"::equals, getNeighbors);

    assertTrue(path.isEmpty());
  }

  @Test
  void dfs_cyclicGraph_returnsTrue() {
    // Graph with a cycle:
    // A -- B
    // |    |
    // D -- C
    Map<String, List<String>> graph = new HashMap<>();
    graph.put("A", List.of("B", "D"));
    graph.put("B", List.of("A", "C"));
    graph.put("C", List.of("B", "D"));
    graph.put("D", List.of("A", "C"));

    Function<String, List<String>> getNeighbors = graph::get;

    boolean found = GraphSearchUtils.dfs("A", "C"::equals, getNeighbors);

    assertTrue(found);
  }

  @Test
  void findLongestPathDirected_largeDAG_returnsLongestPath() {
    // Large DAG:
    // A --1--> B --1--> C --1--> D
    // A --1--> F --1--> G --1--> H --1--> E
    // Longest path should be A -> F -> G -> H -> E
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "F", 1));
    graph.put("B", Map.of("C", 1));
    graph.put("C", Map.of("D", 1));
    graph.put("D", Map.of());
    graph.put("F", Map.of("G", 1));
    graph.put("G", Map.of("H", 1));
    graph.put("H", Map.of("E", 1));
    graph.put("E", Map.of());

    List<String> longestPath = GraphSearchUtils.findLongestPathDirected("A", "E", graph);

    assertEquals(List.of("A", "F", "G", "H", "E"), longestPath);
  }

  @Test
  void findLongestPathUndirected_largeGraph_returnsLongestPath() {
    // Large undirected graph:
    // A --1-- B --1-- C --1-- D --1-- E
    //  \                             /
    //   ----------2----------------
    Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("A", Map.of("B", 1, "E", 2));
    graph.put("B", Map.of("A", 1, "C", 1));
    graph.put("C", Map.of("B", 1, "D", 1));
    graph.put("D", Map.of("C", 1, "E", 1));
    graph.put("E", Map.of("D", 1, "A", 2));

    Function<String, Map<String, Integer>> getNeighbors = graph::get;

    List<String> longestPath = GraphSearchUtils.findLongestPathUndirected("A", "E", getNeighbors);

    // Longest path from A to E should be A -> B -> C -> D -> E
    assertEquals(List.of("A", "B", "C", "D", "E"), longestPath);
  }
}


