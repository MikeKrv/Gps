package com.epam.impl;

public interface Edge {
    <E extends Edge> GraphVertex<E> getVertex();
}
