package org.example.adapter;

import java.util.Collection;
import java.util.Set;

public interface GraphAdapter<V, E> {

    /**
     * @param vertex the vertex to add
     * @return true if the vertex was added successfully
     */
    boolean addVertex(V vertex);

    /**
     * @param edge   the edge identifier
     * @param source the source vertex
     * @param target the target vertex
     * @return true if the edge was added successfully
     */
    boolean addEdge(E edge, V source, V target);

    /**
     * @param vertex the vertex to find neighbors for
     * @return collection of neighboring vertices
     */
    Collection<V> getNeighbors(V vertex);

    /**
     * @return set of all vertices
     */
    Set<V> getVertices();

    /**
     * @param vertex the vertex to check
     * @return true if the vertex exists in the graph
     */
    boolean containsVertex(V vertex);

    /**
     * @return vertex count
     */
    int getVertexCount();
}
