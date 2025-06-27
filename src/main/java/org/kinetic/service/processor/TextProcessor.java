package org.kinetic.service.processor;

import java.util.List;

/**
 * Processes large text files in memory and provides basic statistics
 * such as line count, word count, and word frequency analysis.
 */
public interface TextProcessor {

    /**
     * Returns the number of lines in the processed text.
     *
     * @return total line count
     */
    int getLineCount();


    /**
     * Returns the number of words in the processed text.
     *
     * @return total word count
     */
    int getWordCount();

    /**
     * Returns a list of the most frequent words found in the text.
     *
     * @param top number of top frequent words to return
     * @return list of words sorted by descending frequency
     */
    List<String> getMostFrequentWords(int top);
}
