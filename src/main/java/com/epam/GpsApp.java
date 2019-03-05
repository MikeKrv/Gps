package com.epam;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.impl.GpsNavigatorImpl;
import com.epam.impl.exception.InvalidLineFormatException;
import com.epam.impl.exception.NoPathException;
import com.epam.impl.exception.RouteException;
import com.epam.impl.exception.UnknownVertexException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class app demonstrates how your implementation of {@link com.epam.api.GpsNavigator} is intended to be used.
 */
public class GpsApp {

    public static void main(String[] args) {
        try {
            final GpsNavigator navigator = new GpsNavigatorImpl();
            navigator.readData("D:\\roadmap.txt");

            final Path path = navigator.findPath("A", "C");
            System.out.println(path);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        } catch (IOException | InvalidLineFormatException e) {
            System.out.println("Error reading data: " + e);
        } catch (RouteException e){
            System.out.println(e);
        }
    }
}
