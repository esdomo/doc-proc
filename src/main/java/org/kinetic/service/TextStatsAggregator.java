package org.kinetic.service;

import org.kinetic.data.TextStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TextStatsAggregator {
    private final List<String> fileNames = new ArrayList<>();
    private int wordCount = 0;
    private int lineCount = 0;
    private final Map<String, Integer> wordFreq = new HashMap<>();

    public synchronized void addStats(TextStats stats) {
        if (stats.getFileNames() != null) {
            fileNames.addAll(stats.getFileNames());
        }
        wordCount += stats.getWordCount();
        lineCount += stats.getLineCount();
        if (stats.getWordFreq() != null) {
            stats.getWordFreq().forEach((k, v) ->
                    wordFreq.merge(k, v, Integer::sum));
        }
    }

    public synchronized TextStats aggregate() {
        return TextStats.builder()
                .fileNames(new ArrayList<>(fileNames))
                .wordCount(wordCount)
                .lineCount(lineCount)
                .wordFreq(new HashMap<>(wordFreq))
                .build();
    }
}
