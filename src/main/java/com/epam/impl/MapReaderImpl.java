package com.epam.impl;

import com.epam.api.MapReader;
import com.epam.impl.exception.InvalidLineFormatException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MapReaderImpl implements MapReader {

    private static final int PARTS_OF_THE_ROADMAP = 4;

    @Override
    public Map<String, GraphVertex> readData(String filePath) throws IOException, InvalidLineFormatException {
        Map<String, GraphVertex> roadGraph = new HashMap<>();
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

                addVertexIfMissing(roadGraph, startVertex);
                addVertexIfMissing(roadGraph, endVertex);

                roadGraph.get(startVertex).getEdges().add(
                        new WeightedGraphEdge(roadGraph.get(endVertex), length, cost));

            }
        }

        return roadGraph;
    }

    private void addVertexIfMissing(Map<String, GraphVertex> roadGraph, String vertex) {
        if (!roadGraph.containsKey(vertex)) {
            roadGraph.put(vertex, new GraphVertex(vertex));
        }
    }
}
