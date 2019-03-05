package com.epam.impl;

import java.util.Objects;

public class DistanceWithFewestEdges implements Comparable<DistanceWithFewestEdges> {
    private final int distance;
    private final int minNumEdges;

    public DistanceWithFewestEdges(int distance, int minNumEdges) {
        this.distance = distance;
        this.minNumEdges = minNumEdges;
    }

    public int getDistance() {
        return distance;
    }

    public int getMinNumEdges() {
        return minNumEdges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceWithFewestEdges that = (DistanceWithFewestEdges) o;
        return distance == that.distance &&
                minNumEdges == that.minNumEdges;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, minNumEdges);
    }

    @Override
    public int compareTo(DistanceWithFewestEdges o) {
        int result = Integer.compare(distance, o.distance);
        return  result != 0 ? result : Integer.compare(minNumEdges, o.minNumEdges);
    }
}
