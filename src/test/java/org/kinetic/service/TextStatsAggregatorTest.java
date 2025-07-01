package org.kinetic.service;

import org.junit.jupiter.api.Test;
import org.kinetic.data.TextStats;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStatsAggregatorTest {

    @Test
    void shouldAggregateMultipleStats() {
        TextStats stats1 = TextStats.builder()
                .fileNames(List.of("a.txt"))
                .lineCount(2)
                .wordCount(4)
                .wordsFreq(new java.util.HashMap<>(Map.of("hello", 2)))
                .build();

        TextStats stats2 = TextStats.builder()
                .fileNames(List.of("b.txt"))
                .lineCount(3)
                .wordCount(6)
                .wordsFreq(new java.util.HashMap<>(Map.of("world", 3)))
                .build();

        TextStatsAggregator aggregator = new TextStatsAggregator();
        aggregator.addStats(stats1);
        aggregator.addStats(stats2);

        TextStats result = aggregator.aggregate();
        assertEquals(List.of("a.txt", "b.txt"), result.getFileNames());
        assertEquals(10, result.getWordCount());
        assertEquals(5, result.getLineCount());
        assertEquals(2, result.getWordsFreq().size());
        assertEquals(2, result.getWordsFreq().get("hello"));
        assertEquals(3, result.getWordsFreq().get("world"));
    }
}