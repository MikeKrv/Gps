package com.epam.impl;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.impl.exception.InvalidLineFormatException;
import com.epam.impl.exception.NoPathException;
import com.epam.impl.exception.RouteException;
import com.epam.impl.exception.UnknownVertexException;

import java.io.*;
import java.util.*;

public class GpsNavigatorImpl implements GpsNavigator {

    private static final int PARTS_OF_THE_ROADMAP = 4;
    private static final int FIRST_ELEMENT = 0;

    private Map<String, GraphVertex> roadGraph = new HashMap<>();
    private List<String> routes = new ArrayList<>();
    private List<Integer> distance = new ArrayList<>();

    @Override
    public void readData(String filePath) throws IOException, InvalidLineFormatException {
        File inputFile = new File(filePath);

        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {

            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] partsOfTheRoad = strLine.split(" ");
                if (partsOfTheRoad.length != PARTS_OF_THE_ROADMAP) {
                    throw new InvalidLineFormatException("Invalid format of input line: " + strLine);
                }
                String startVertex = partsOfTheRoad[0];
                String endVertex = partsOfTheRoad[1];
                int length = Integer.parseInt(partsOfTheRoad[2]);
                int cost = Integer.parseInt(partsOfTheRoad[3]);

                int distance = length * cost;

                addVertexIfMissing(startVertex);
                addVertexIfMissing(endVertex);
                roadGraph.get(endVertex).setDistance(distance);
                roadGraph.get(startVertex).getEdges().add(roadGraph.get(endVertex));

            }
        }

    }

    private void addVertexIfMissing(String vertex) {
        if (!roadGraph.containsKey(vertex)) {
            roadGraph.put(vertex, new GraphVertex(vertex));
        }
    }

    @Override
    public Path findPath(String pointA, String pointB) throws RouteException {
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

            for (GraphVertex v : u.getEdges()) {
                int vDistance = u.getDistanceWithFewestEdges().getDistance() + v.getDistance();
                int vNumEdges = u.getDistanceWithFewestEdges().getMinNumEdges() + 1;
                if (v.getDistanceWithFewestEdges().getDistance() > vDistance
                        || (v.getDistanceWithFewestEdges().getDistance() == vDistance
                        && v.getDistanceWithFewestEdges().getMinNumEdges() > vNumEdges)) {
                    nodeSet.remove(v);
                    v.setPrevious(u);
                    v.setDistanceWithFewestEdges(new DistanceWithFewestEdges(vDistance, vNumEdges));
                    nodeSet.add(v);
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
            throw new NoPathException("There is no route between points");
        }
        distance.add(vertexDistance);
        if (previousExists) {
            outputShortestPath(previous);
        }
        routes.add(endVertex.getName());

    }
}
