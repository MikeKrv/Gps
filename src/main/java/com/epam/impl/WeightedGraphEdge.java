package com.epam.impl;

public class WeightedGraphEdge implements Edge {
    private final GraphVertex<WeightedGraphEdge> vertex;
    private final int length;
    private final int cost;

    public WeightedGraphEdge(GraphVertex<WeightedGraphEdge> vertex, int length, int cost) {
        this.vertex = vertex;
        this.length = length;
        this.cost = cost;
    }

    @Override
    public GraphVertex<WeightedGraphEdge> getVertex() {
        return vertex;
    }

    public int getLength() {
        return length;
    }

    public int getCost() {
        return cost;
    }
}
