package io.github.pixzarpg.core.api.datapacks;

/**
 * Responsible for parsing input and retrieving a data pack
 * @param <T> The type of the input to be parsed
 */
public interface APIDataPackParser<T> {

    /**
     * Extract data from the given input and prepare a datapack object from it
     * @param input
     */
    APIDataPack parse(T input);

}
