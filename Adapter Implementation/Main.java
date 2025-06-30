package org.example;

import edu.uci.ics.jung.graph.SparseMultigraph;
import org.example.adapter.JungGraphAdapter;
import org.example.adapter.GraphAdapter;
import org.example.graphTravelers.BfsGraphTraverser;
import org.example.graphTravelers.DfsGraphTraverser;
import org.example.graphTravelers.Traverser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a JUNG graph
        SparseMultigraph<Integer, String> jungGraph = new SparseMultigraph<>();

        jungGraph.addVertex(1);
        jungGraph.addVertex(2);
        jungGraph.addVertex(3);
        jungGraph.addVertex(4);
        jungGraph.addVertex(5);

        jungGraph.addEdge("E1", 1, 2); // Edge between Vertex 1 and Vertex 2
        jungGraph.addEdge("E2", 1, 3); // Edge between Vertex 1 and Vertex 3
        jungGraph.addEdge("E3", 2, 4); // Edge between Vertex 2 and Vertex 4
        jungGraph.addEdge("E4", 3, 5); // Edge between Vertex 3 and Vertex 5
        jungGraph.addEdge("E5", 4, 5); // Edge between Vertex 4 and Vertex 5

        GraphAdapter<Integer, String> graphAdapter = new JungGraphAdapter(jungGraph);

        // Create traversers using the adapter
        Traverser dfsGraphTraveler = new DfsGraphTraverser(graphAdapter);
        Traverser bfsGraphTraveler = new BfsGraphTraverser(graphAdapter);

        // Perform traversals
        List<Integer> dfsPath = dfsGraphTraveler.traverse(1);
        List<Integer> bfsPath = bfsGraphTraveler.traverse(1);

        System.out.println("=== Step 1: Using JUNG with Adapter Pattern ===");
        System.out.println("Graph-DFS From node 1 is : " + dfsPath);
        System.out.println("Graph-BFS From node 1 is : " + bfsPath);
    }
}
