package com.epam.impl;

public class WeightedGraphEdge {
    private final GraphVertex vertex;
    private final int length;
    private final int cost;

    public WeightedGraphEdge(GraphVertex vertex, int length, int cost) {
        this.vertex = vertex;
        this.length = length;
        this.cost = cost;
    }

    public GraphVertex getVertex() {
        return vertex;
    }

    public int getLength() {
        return length;
    }

    public int getCost() {
        return cost;
    }
}
