package com.epam.impl;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.impl.exception.NoPathException;
import com.epam.impl.exception.RouteException;
import com.epam.impl.exception.UnknownVertexException;

import java.util.*;

public class GpsNavigatorImpl implements GpsNavigator {
    private static final int FIRST_ELEMENT = 0;
    private List<String> routes = new ArrayList<>();
    private List<Integer> distance = new ArrayList<>();

    @Override
    public Path findPath(Map<String, GraphVertex> roadGraph, String pointA, String pointB) throws RouteException {
        GraphVertex startPoint = roadGraph.get(pointA);
        if (startPoint == null) {
            throw new UnknownVertexException("There is no start point on the map: " + pointA);
        }
        GraphVertex endPoint = roadGraph.get(pointB);
        if (endPoint == null) {
            throw new UnknownVertexException("There is no end point on the map: " + pointB);
        }

        startPoint.setDistanceWithFewestEdges(new DistanceWithFewestEdges(0, 0));
        SortedSet<GraphVertex> nodeSet = new TreeSet<>();
        nodeSet.add(startPoint);

        while (!nodeSet.isEmpty()) {
            GraphVertex u = nodeSet.first();
            if (u.equals(endPoint)) {
                break;
            }
            nodeSet.remove(nodeSet.first());

            for (WeightedGraphEdge v : u.getEdges()) {
                int vDistance = u.getDistanceWithFewestEdges().getDistance() + v.getLength() * v.getCost();
                int vNumEdges = u.getDistanceWithFewestEdges().getMinNumEdges() + 1;
                if (v.getVertex().getDistanceWithFewestEdges().getDistance() > vDistance
                        || (v.getVertex().getDistanceWithFewestEdges().getDistance() == vDistance
                        && v.getVertex().getDistanceWithFewestEdges().getMinNumEdges() > vNumEdges)) {
                    nodeSet.remove(v.getVertex());
                    v.getVertex().setPrevious(u);
                    v.getVertex().setDistanceWithFewestEdges(new DistanceWithFewestEdges(vDistance, vNumEdges));
                    nodeSet.add(v.getVertex());
                }
            }
        }

        outputShortestPath(endPoint);

        return new Path(routes, distance.get(FIRST_ELEMENT));
    }

    private void outputShortestPath(GraphVertex endVertex) throws NoPathException {
        int vertexDistance = endVertex.getDistanceWithFewestEdges().getDistance();
        GraphVertex previous = endVertex.getPrevious();
        boolean previousExists = previous != null;
        if (vertexDistance == GraphVertex.NO_DISTANCE) {
            throw new NoPathException("There is not route between points");
        }
        distance.add(vertexDistance);
        if (previousExists) {
            outputShortestPath(previous);
        }
        routes.add(endVertex.getName());

    }
}
