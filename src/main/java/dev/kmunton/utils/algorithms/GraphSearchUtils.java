package dev.kmunton.utils.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class GraphSearchUtils {

  /**
   * Performs the A* search algorithm to find the shortest path from the start node to the goal node.
   *
   * @param start       The starting node.
   * @param goal        The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param heuristic   Function to estimate the cost from a node to the goal.
   * @param <T>         The type of the nodes.
   * @return A list representing the shortest path from start to goal, or an empty list if no path is found.
   */
  public static <T> List<T> aStarSearch(
      T start,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors,
      ToIntFunction<T> heuristic
  ) {
    PriorityQueue<Node<T>> openSet = new PriorityQueue<>();
    Map<T, Integer> gCosts = new HashMap<>();
    Map<T, T> cameFrom = new HashMap<>();
    Set<T> openSetTracker = new HashSet<>();

    openSet.add(new Node<>(start, 0, heuristic.applyAsInt(start)));
    gCosts.put(start, 0);
    openSetTracker.add(start);

    while (!openSet.isEmpty()) {
      Node<T> current = openSet.poll();
      openSetTracker.remove(current.value);

      if (current.value.equals(goal)) {
        return reconstructPath(cameFrom, goal);
      }

      for (Map.Entry<T, Integer> neighborEntry : getNeighbors.apply(current.value).entrySet()) {
        T neighbor = neighborEntry.getKey();
        int tentativeGCost = gCosts.get(current.value) + neighborEntry.getValue();

        if (tentativeGCost < gCosts.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          cameFrom.put(neighbor, current.value);
          gCosts.put(neighbor, tentativeGCost);

          int fCost = tentativeGCost + heuristic.applyAsInt(neighbor);
          if (!openSetTracker.contains(neighbor)) {
            openSet.add(new Node<>(neighbor, tentativeGCost, fCost));
            openSetTracker.add(neighbor);
          }
        }
      }
    }

    return List.of(); // No path found
  }

  /**
   * Performs Dijkstra's algorithm to find the shortest path from the start node to the goal node.
   *
   * @param start        The starting node.
   * @param goal         The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param <T>          The type of the nodes.
   * @return A list representing the shortest path from start to goal, or an empty list if no path is found.
   */
  public static <T> List<T> dijkstraSearch(
      T start,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors
  ) {
    PriorityQueue<Node<T>> queue = new PriorityQueue<>();
    Map<T, Integer> gCosts = new HashMap<>();
    Map<T, T> cameFrom = new HashMap<>();
    Set<T> queueTracker = new HashSet<>();

    queue.add(new Node<>(start, 0, 0));
    gCosts.put(start, 0);
    queueTracker.add(start);

    while (!queue.isEmpty()) {
      Node<T> current = queue.poll();
      queueTracker.remove(current.value);

      if (current.value.equals(goal)) {
        return reconstructPath(cameFrom, goal);
      }

      for (Map.Entry<T, Integer> neighborEntry : getNeighbors.apply(current.value).entrySet()) {
        T neighbor = neighborEntry.getKey();
        int tentativeGCost = gCosts.get(current.value) + neighborEntry.getValue();

        if (tentativeGCost < gCosts.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          cameFrom.put(neighbor, current.value);
          gCosts.put(neighbor, tentativeGCost);

          if (!queueTracker.contains(neighbor)) {
            queue.add(new Node<>(neighbor, tentativeGCost, 0)); // fCost not used in Dijkstra
            queueTracker.add(neighbor);
          }
        }
      }
    }

    return Collections.emptyList(); // No path found
  }

  /**
   * Performs Breadth-First Search (BFS) to find the shortest path (in terms of number of steps) from the start node to a node satisfying the goal predicate.
   *
   * @param start        The starting node.
   * @param isGoal       Predicate to test if a node is the goal.
   * @param getNeighbors Function to get neighbors for a given node.
   * @param <T>          The type of the nodes.
   * @return A list representing the shortest path from start to the goal node, or an empty list if no path is found.
   */
  public static <T> List<T> bfs(
      T start,
      Predicate<T> isGoal,
      Function<T, List<T>> getNeighbors
  ) {
    Queue<List<T>> queue = new LinkedList<>();
    Set<T> visited = new HashSet<>();
    queue.add(Collections.singletonList(start));
    visited.add(start);

    while (!queue.isEmpty()) {
      List<T> path = queue.poll();
      T current = path.getLast();

      if (isGoal.test(current)) {
        return path;
      }

      for (T neighbor : getNeighbors.apply(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          List<T> newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          queue.add(newPath);
        }
      }
    }

    return List.of(); // No path found
  }

  /**
   * Performs Depth-First Search (DFS) to determine if there is a path from the start node to a node satisfying the goal predicate.
   *
   * @param start        The starting node.
   * @param isGoal       Predicate to test if a node is the goal.
   * @param getNeighbors Function to get neighbors for a given node.
   * @param <T>          The type of the nodes.
   * @return True if a path to the goal node exists, false otherwise.
   */
  public static <T> boolean dfs(
      T start,
      Predicate<T> isGoal,
      Function<T, List<T>> getNeighbors
  ) {
    Deque<T> stack = new ArrayDeque<>();
    Set<T> visited = new HashSet<>();
    stack.push(start);

    while (!stack.isEmpty()) {
      T current = stack.pop();
      if (visited.contains(current)) continue;
      visited.add(current);

      if (isGoal.test(current)) {
        return true;
      }

      for (T neighbor : getNeighbors.apply(current)) {
        if (!visited.contains(neighbor)) {
          stack.push(neighbor);
        }
      }
    }

    return false;
  }

  /**
   * Reconstructs the path from the start node to the goal node using the cameFrom map.
   *
   * @param cameFrom Map of nodes to their predecessors.
   * @param goal     The goal node.
   * @param <T>      The type of the nodes.
   * @return A list representing the path from start to goal.
   */
  private static <T> List<T> reconstructPath(Map<T, T> cameFrom, T goal) {
    LinkedList<T> path = new LinkedList<>();
    T current = goal;

    while (current != null) {
      path.addFirst(current);
      current = cameFrom.get(current);
    }

    return path;
  }

  /**
   * Inner class representing a node in the search algorithms.
   *
   * @param <T> The type of the node value.
   */
  private static class Node<T> implements Comparable<Node<T>> {
    final T value;
    final int gCost; // Cost from start to this node
    final int fCost; // Estimated total cost (gCost + heuristic)

    Node(T value, int gCost, int fCost) {
      this.value = value;
      this.gCost = gCost;
      this.fCost = fCost;
    }

    @Override
    public int compareTo(Node<T> other) {
      return Integer.compare(this.fCost, other.fCost);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node<?> node)) return false;
      return value.equals(node.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }

  /**
   * Finds the longest path from the start node to the goal node in a directed acyclic graph (DAG).
   *
   * @param start  The starting node.
   * @param goal   The goal node.
   * @param graph  The graph represented as a map of nodes to their neighbors and edge weights.
   * @param <T>    The type of the nodes.
   * @return A list representing the longest path from start to goal, or an empty list if no path exists.
   */
  public static <T> List<T> findLongestPathDirected(
      T start,
      T goal,
      Map<T, Map<T, Integer>> graph
  ) {
    Function<T, Map<T, Integer>> getNeighbors = graph::get;

    // Perform topological sort on the entire graph
    List<T> topoOrder = topologicalSort(graph);
    if (topoOrder.isEmpty() || !topoOrder.contains(goal)) {
      return List.of(); // Graph is not a DAG or goal is unreachable
    }

    // Initialize distances and predecessors
    Map<T, Integer> distances = new HashMap<>();
    Map<T, T> predecessors = new HashMap<>();
    for (T node : topoOrder) {
      distances.put(node, Integer.MIN_VALUE);
    }
    distances.put(start, 0);

    // Process nodes in topological order
    for (T node : topoOrder) {
      if (distances.get(node) == Integer.MIN_VALUE) continue; // Skip unreachable nodes

      Map<T, Integer> neighbors = getNeighbors.apply(node);
      if (neighbors == null) {
        neighbors = Map.of();
      }

      for (Map.Entry<T, Integer> neighborEntry : neighbors.entrySet()) {
        T neighbor = neighborEntry.getKey();
        int weight = neighborEntry.getValue();
        int newDistance = distances.get(node) + weight;

        if (newDistance > distances.getOrDefault(neighbor, Integer.MIN_VALUE)) {
          distances.put(neighbor, newDistance);
          predecessors.put(neighbor, node);
        }
      }
    }

    // Reconstruct the path from start to goal
    if (!distances.containsKey(goal) || distances.get(goal) == Integer.MIN_VALUE) {
      return List.of(); // Goal is unreachable
    }
    return reconstructPath(predecessors, goal);
  }

  /**
   * Finds the longest path from the start node to the goal node in an undirected graph.
   *
   * @param start        The starting node.
   * @param goal         The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param <T>          The type of the nodes.
   * @return A list representing the longest path from start to goal, or an empty list if no path exists.
   */
  public static <T> List<T> findLongestPathUndirected(
      T start,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors
  ) {
    Set<T> visited = new HashSet<>();
    List<T> longestPath = new ArrayList<>();
    List<T> currentPath = new ArrayList<>();
    currentPath.add(start);

    int[] maxLength = new int[]{0}; // Initialize to 0
    findLongestPathDFS(start, goal, getNeighbors, visited, currentPath, longestPath, 0, maxLength);

    return longestPath;
  }

  /**
   * Helper method for DFS traversal to find the longest path in an undirected graph.
   *
   * @param current      The current node.
   * @param goal         The goal node.
   * @param getNeighbors Function to get neighbors and their edge weights for a given node.
   * @param visited      Set of visited nodes.
   * @param currentPath  The current path being explored.
   * @param longestPath  The longest path found so far.
   * @param currentLength The length of the current path.
   * @param maxLength    The maximum length found so far.
   * @param <T>          The type of the nodes.
   */
  @SuppressWarnings("java:S107")
  private static <T> void findLongestPathDFS(
      T current,
      T goal,
      Function<T, Map<T, Integer>> getNeighbors,
      Set<T> visited,
      List<T> currentPath,
      List<T> longestPath,
      int currentLength,
      int[] maxLength
  ) {
    if (current.equals(goal)) {
      if (currentLength >= maxLength[0]) {
        maxLength[0] = currentLength;
        longestPath.clear();
        longestPath.addAll(new ArrayList<>(currentPath));
      }
      return;
    }

    visited.add(current);

    // Safely get neighbors
    Map<T, Integer> neighbors = getNeighbors.apply(current);
    if (neighbors == null) {
      neighbors = Collections.emptyMap();
    }

    for (Map.Entry<T, Integer> neighborEntry : neighbors.entrySet()) {
      T neighbor = neighborEntry.getKey();
      int weight = neighborEntry.getValue();

      if (!visited.contains(neighbor)) {
        currentPath.add(neighbor);
        findLongestPathDFS(
            neighbor,
            goal,
            getNeighbors,
            visited,
            currentPath,
            longestPath,
            currentLength + weight,
            maxLength
        );
        currentPath.removeLast();
      }
    }

    visited.remove(current);
  }

  /**
   * Performs topological sorting on a directed graph.
   *
   * @param graph The graph represented as a map of nodes to their neighbors and edge weights.
   * @param <T>   The type of the nodes.
   * @return A list representing the topological order of nodes, or an empty list if the graph contains a cycle.
   */
  private static <T> List<T> topologicalSort(Map<T, Map<T, Integer>> graph) {
    Set<T> visited = new HashSet<>();
    Deque<T> stack = new ArrayDeque<>();

    for (T node : graph.keySet()) {
      if (!visited.contains(node) && dfsTopologicalSort(node, graph, visited, new HashSet<>(), stack)) {
          return List.of(); // Graph contains a cycle
        }

    }

    // Collect nodes from the stack into the topological order list
    List<T> topoOrder = new ArrayList<>();
    while (!stack.isEmpty()) {
      topoOrder.add(stack.pop());
    }
    return topoOrder;
  }

  /**
   * Helper method for DFS traversal during topological sorting.
   *
   * @param node           The current node.
   * @param graph          The graph represented as a map.
   * @param visited        Set of visited nodes.
   * @param recursionStack Set of nodes in the current recursion stack to detect cycles.
   * @param stack          Stack to store the topological order.
   * @param <T>            The type of the nodes.
   * @return True if a cycle is detected, false otherwise.
   */
  private static <T> boolean dfsTopologicalSort(
      T node,
      Map<T, Map<T, Integer>> graph,
      Set<T> visited,
      Set<T> recursionStack,
      Deque<T> stack
  ) {
    visited.add(node);
    recursionStack.add(node);

    Map<T, Integer> neighbors = graph.getOrDefault(node, Collections.emptyMap());
    for (T neighbor : neighbors.keySet()) {
      if (recursionStack.contains(neighbor)) {
        return true; // Cycle detected
      }
      if (!visited.contains(neighbor) && dfsTopologicalSort(neighbor, graph, visited, recursionStack, stack)) {
          return true; // Cycle detected
        }

    }

    recursionStack.remove(node);
    stack.push(node);
    return false;
  }
}


