package com.epam.impl;

public class VertexWithDistance {
    private final GraphVertex vertex;
    private final Integer distance;

    public VertexWithDistance(GraphVertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public GraphVertex getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }
}
