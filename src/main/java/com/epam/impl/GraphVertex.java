package com.epam.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphVertex implements Comparable<GraphVertex> {
    public static final int NO_DISTANCE = Integer.MAX_VALUE;
    private DistanceWithFewestEdges distanceWithFewestEdges
            = new DistanceWithFewestEdges(NO_DISTANCE, 0);
    private List<VertexWithDistance> edges = new ArrayList<>();
    private String name;
    private GraphVertex previous = null;

    public GraphVertex(String name) {
        this.name = name;
    }

    public DistanceWithFewestEdges getDistanceWithFewestEdges() {
        return distanceWithFewestEdges;
    }

    public void setDistanceWithFewestEdges(DistanceWithFewestEdges distanceWithFewestEdges) {
        this.distanceWithFewestEdges = distanceWithFewestEdges;
    }

    public List<VertexWithDistance> getEdges() {
        return edges;
    }

    public String getName() {
        return name;
    }

    public GraphVertex getPrevious() {
        return previous;
    }

    public void setPrevious(GraphVertex previous) {
        this.previous = previous;
    }

    @Override
    public int compareTo(GraphVertex o) {
        int result = distanceWithFewestEdges.compareTo(o.distanceWithFewestEdges);
        return result != 0 ? result : name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GraphVertex)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GraphVertex that = (GraphVertex)obj;
        return name.equals(that.name)
                && distanceWithFewestEdges.equals(that.distanceWithFewestEdges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, distanceWithFewestEdges);
    }
}
