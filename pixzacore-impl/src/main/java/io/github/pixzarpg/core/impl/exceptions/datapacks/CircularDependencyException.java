package io.github.pixzarpg.core.impl.exceptions.datapacks;

import java.io.IOException;

/**
 * Used when a data pack cannot be loaded due it or it's dependencies requiring each other
 */
public class CircularDependencyException extends IOException {

    public CircularDependencyException(String message) {
        super(message);
    }

}
