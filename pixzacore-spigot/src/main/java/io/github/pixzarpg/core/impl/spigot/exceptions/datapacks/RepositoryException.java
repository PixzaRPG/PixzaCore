package io.github.pixzarpg.core.impl.spigot.exceptions.datapacks;

/**
 * Thrown when a repository method encounters an error
 */
public class RepositoryException extends Exception {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
