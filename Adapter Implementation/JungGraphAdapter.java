package org.example.adapter;

import edu.uci.ics.jung.graph.SparseMultigraph;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JungGraphAdapter implements GraphAdapter<Integer, String> {

    private final SparseMultigraph<Integer, String> jungGraph;

    /**
     * @param jungGraph the JUNG graph to wrap
     */
    public JungGraphAdapter(SparseMultigraph<Integer, String> jungGraph) {
        this.jungGraph = jungGraph;
    }

    public JungGraphAdapter() {
        this.jungGraph = new SparseMultigraph<>();
    }

    @Override
    public boolean addVertex(Integer vertex) {
        return jungGraph.addVertex(vertex);
    }

    @Override
    public boolean addEdge(String edge, Integer source, Integer target) {
        return jungGraph.addEdge(edge, source, target);
    }

    @Override
    public Collection<Integer> getNeighbors(Integer vertex) {
        return jungGraph.getNeighbors(vertex);
    }

    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(jungGraph.getVertices());
    }

    @Override
    public boolean containsVertex(Integer vertex) {
        return jungGraph.containsVertex(vertex);
    }

    @Override
    public int getVertexCount() {
        return jungGraph.getVertexCount();
    }

    /**
     * @return the wrapped JUNG graph
     */
    public SparseMultigraph<Integer, String> getJungGraph() {
        return jungGraph;
    }
}
