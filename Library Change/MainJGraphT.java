package org.example;

import org.example.adapter.JGraphTAdapter;
import org.example.adapter.GraphAdapter;
import org.example.graphTravelers.BfsGraphTraverser;
import org.example.graphTravelers.DfsGraphTraverser;
import org.example.graphTravelers.Traverser;

import java.util.List;

public class MainJGraphT {
    public static void main(String[] args) {
        GraphAdapter<Integer, String> graphAdapter = new JGraphTAdapter();

        graphAdapter.addVertex(1);
        graphAdapter.addVertex(2);
        graphAdapter.addVertex(3);
        graphAdapter.addVertex(4);
        graphAdapter.addVertex(5);

        graphAdapter.addEdge("E1", 1, 2); // Edge between Vertex 1 and Vertex 2
        graphAdapter.addEdge("E2", 1, 3); // Edge between Vertex 1 and Vertex 3
        graphAdapter.addEdge("E3", 2, 4); // Edge between Vertex 2 and Vertex 4
        graphAdapter.addEdge("E4", 3, 5); // Edge between Vertex 3 and Vertex 5
        graphAdapter.addEdge("E5", 4, 5); // Edge between Vertex 4 and Vertex 5

        Traverser dfsGraphTraveler = new DfsGraphTraverser(graphAdapter);
        Traverser bfsGraphTraveler = new BfsGraphTraverser(graphAdapter);

        List<Integer> dfsPath = dfsGraphTraveler.traverse(1);
        List<Integer> bfsPath = bfsGraphTraveler.traverse(1);

        System.out.println("=== Step 2: Using JGraphT with Adapter Pattern ===");
        System.out.println("Graph-DFS From node 1 is : " + dfsPath);
        System.out.println("Graph-BFS From node 1 is : " + bfsPath);

        System.out.println("Notice: Traverser classes remained completely unchanged!");
    }
}
