package com.epam.api;

import com.epam.impl.Edge;
import com.epam.impl.GraphVertex;
import com.epam.impl.exception.InvalidLineFormatException;

import java.io.IOException;
import java.util.Map;

public interface RoadGraphReader<E extends Edge> {
    /**
     *
     * @param filePath path to file, which contains data in the following format:
     *                 A B 3 2
     *                 A C 4 2
     *                 C B 5 3
     *                 D E 7 4
     *                 C D 4 5
     *                 D Pizza 7 8
     *                 Pizza Metro 9 9
     *                 etc.
     *                 Where the first two columns represent the road considering its direction, the third one represents
     *                 its length and the fourth one represents cost of transportation.
     * @return
     */
    Map<String, GraphVertex<E>> readData(String filePath) throws IOException, InvalidLineFormatException;
}
