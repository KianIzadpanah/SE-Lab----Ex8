package org.example.graphTravelers;

import org.example.adapter.GraphAdapter;
import java.util.*;

public class BfsGraphTraverser implements Traverser {
    private final GraphAdapter<Integer, String> graphAdapter;

    /**
     * @param graphAdapter the graph adapter to use for traversal
     */
    public BfsGraphTraverser(GraphAdapter<Integer, String> graphAdapter) {
        this.graphAdapter = graphAdapter;
    }

    @Override
    public List<Integer> traverse(Integer startVertex) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        if (!graphAdapter.containsVertex(startVertex)) {
            return result;
        }

        queue.add(startVertex);
        visited.add(startVertex);

        while (!queue.isEmpty()) {
            Integer vertex = queue.poll();
            result.add(vertex);

            List<Integer> neighbors = new ArrayList<>(graphAdapter.getNeighbors(vertex));
            neighbors.sort(Comparator.naturalOrder());

            for (Integer neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }
}
