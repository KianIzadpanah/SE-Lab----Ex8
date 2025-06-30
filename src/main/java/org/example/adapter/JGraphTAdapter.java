package org.example.adapter;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.util.*;

public class JGraphTAdapter implements GraphAdapter<Integer, String> {

    private final Graph<Integer, DefaultEdge> jgraphtGraph;
    private final Map<String, DefaultEdge> edgeIdentifiers;

    /**
     * @param jgraphtGraph the JGraphT graph to wrap
     */
    public JGraphTAdapter(Graph<Integer, DefaultEdge> jgraphtGraph) {
        this.jgraphtGraph = jgraphtGraph;
        this.edgeIdentifiers = new HashMap<>();
    }

    public JGraphTAdapter() {
        this.jgraphtGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        this.edgeIdentifiers = new HashMap<>();
    }

    @Override
    public boolean addVertex(Integer vertex) {
        return jgraphtGraph.addVertex(vertex);
    }

    @Override
    public boolean addEdge(String edge, Integer source, Integer target) {
        try {
            DefaultEdge addedEdge = jgraphtGraph.addEdge(source, target);
            if (addedEdge != null) {
                edgeIdentifiers.put(edge, addedEdge);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Collection<Integer> getNeighbors(Integer vertex) {
        Set<Integer> neighbors = new HashSet<>();
        Set<DefaultEdge> edges = jgraphtGraph.edgesOf(vertex);

        for (DefaultEdge edge : edges) {
            Integer source = jgraphtGraph.getEdgeSource(edge);
            Integer target = jgraphtGraph.getEdgeTarget(edge);

            if (source.equals(vertex)) {
                neighbors.add(target);
            } else {
                neighbors.add(source);
            }
        }

        return neighbors;
    }

    @Override
    public Set<Integer> getVertices() {
        return jgraphtGraph.vertexSet();
    }

    @Override
    public boolean containsVertex(Integer vertex) {
        return jgraphtGraph.containsVertex(vertex);
    }

    @Override
    public int getVertexCount() {
        return jgraphtGraph.vertexSet().size();
    }

    /**
     * @return the wrapped JGraphT graph
     */
    public Graph<Integer, DefaultEdge> getJGraphTGraph() {
        return jgraphtGraph;
    }

    /**
     * @return map of string identifiers to edges
     */
    public Map<String, DefaultEdge> getEdgeIdentifiers() {
        return edgeIdentifiers;
    }
}
