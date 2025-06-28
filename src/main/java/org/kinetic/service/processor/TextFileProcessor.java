package org.kinetic.service.processor;

import org.kinetic.data.TextStats;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Processes large text files in memory and provides basic statistics
 * such as line count, word count, and word frequency analysis.
 */
public interface TextFileProcessor {

    /**
     * Load file and process the given text file to store stats
     */
    void processFile(Path path) throws IOException;

    /**
     * Returns
     *
     * @return TextStats with word count, line count, word frequencies, etc..
     */
    TextStats stats();
}
