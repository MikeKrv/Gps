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
    public <E extends Edge> Path findPath(Map<String, GraphVertex<E>> roadGraph, String pointA, String pointB,
                                          DistanceCalculator<E> calculator) throws RouteException {
        GraphVertex<E> startPoint = roadGraph.get(pointA);
        if (startPoint == null) {
            throw new UnknownVertexException("There is no start point on the map: " + pointA);
        }
        GraphVertex<E> endPoint = roadGraph.get(pointB);
        if (endPoint == null) {
            throw new UnknownVertexException("There is no end point on the map: " + pointB);
        }

        startPoint.setDistanceWithFewestEdges(new DistanceWithFewestEdges(0, 0));
        SortedSet<GraphVertex<E>> nodeSet = new TreeSet<>();
        nodeSet.add(startPoint);

        while (!nodeSet.isEmpty()) {
            GraphVertex<E> u = nodeSet.first();
            if (u.equals(endPoint)) {
                break;
            }
            nodeSet.remove(nodeSet.first());

            for (E v : u.getEdges()) {
                int vDistance = u.getDistanceWithFewestEdges().getDistance() + calculator.calculateDistance(v);
                int vNumEdges = u.getDistanceWithFewestEdges().getMinNumEdges() + 1;
                if (v.getVertex().getDistanceWithFewestEdges().getDistance() > vDistance
                        || (v.getVertex().getDistanceWithFewestEdges().getDistance() == vDistance
                        && v.getVertex().getDistanceWithFewestEdges().getMinNumEdges() > vNumEdges)) {
                    nodeSet.remove(v.getVertex());
                    v.getVertex().setPrevious((GraphVertex<Edge>) u);
                    v.getVertex().setDistanceWithFewestEdges(new DistanceWithFewestEdges(vDistance, vNumEdges));
                    nodeSet.add(v.getVertex());
                }
            }
        }

        outputShortestPath(endPoint);

        return new Path(routes, distance.get(FIRST_ELEMENT));
    }

    private <E extends Edge> void outputShortestPath(GraphVertex<E> endVertex) throws NoPathException {
        int vertexDistance = endVertex.getDistanceWithFewestEdges().getDistance();
        GraphVertex<E> previous = endVertex.getPrevious();
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
