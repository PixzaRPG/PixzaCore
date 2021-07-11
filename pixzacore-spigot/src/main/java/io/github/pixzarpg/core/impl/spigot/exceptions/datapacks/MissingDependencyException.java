package io.github.pixzarpg.core.impl.spigot.exceptions.datapacks;

import java.io.IOException;

/**
 * Used when a data pack is missing
 */
public class MissingDependencyException extends IOException {

    public MissingDependencyException(String message) {
        super(message);
    }

}
