package org.example.graphTravelers;

import org.example.adapter.GraphAdapter;
import java.util.*;

public class DfsGraphTraverser implements Traverser {
    private final GraphAdapter<Integer, String> graphAdapter;

    /**
     * @param graphAdapter the graph adapter to use for traversal
     */
    public DfsGraphTraverser(GraphAdapter<Integer, String> graphAdapter) {
        this.graphAdapter = graphAdapter;
    }

    @Override
    public List<Integer> traverse(Integer startVertex) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        if (!graphAdapter.containsVertex(startVertex)) {
            return result;
        }

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Integer vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                result.add(vertex);

                List<Integer> neighbors = new ArrayList<>(graphAdapter.getNeighbors(vertex));
                neighbors.sort(Collections.reverseOrder());

                for (Integer neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return result;
    }
}