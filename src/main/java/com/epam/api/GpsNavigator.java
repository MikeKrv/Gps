package com.epam.api;

import com.epam.impl.DistanceCalculator;
import com.epam.impl.Edge;
import com.epam.impl.GraphVertex;
import com.epam.impl.exception.RouteException;

import java.util.Map;

public interface GpsNavigator {
    /**
     * Find path between two points.
     *
     * @param pointA starting point.
     * @param pointB end point.
     * @return object, which describes the found path.
     */
    <E extends Edge> Path findPath(Map<String, GraphVertex<E>> roadGraph, String pointA, String pointB,
                                   DistanceCalculator<E> calculator) throws RouteException;
}
