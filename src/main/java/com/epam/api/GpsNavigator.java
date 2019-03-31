package com.epam.api;

import com.epam.impl.GraphVertex;
import com.epam.impl.exception.InvalidLineFormatException;
import com.epam.impl.exception.NoPathException;
import com.epam.impl.exception.RouteException;
import com.epam.impl.exception.UnknownVertexException;

import java.io.IOException;
import java.util.Map;

public interface GpsNavigator {



    /**
     * Find path between two points.
     *
     * @param pointA starting point.
     * @param pointB end point.
     * @return object, which describes the found path.
     */
    Path findPath(Map<String, GraphVertex> roadGraph, String pointA, String pointB) throws RouteException;
}
