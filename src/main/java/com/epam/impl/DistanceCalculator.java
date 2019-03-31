package com.epam.impl;

@FunctionalInterface
public interface DistanceCalculator<T> {
    int calculateDistance(T t);
}
