package org.kinetic.service.processor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTextProcessor implements TextProcessor {
    private int lineCount = 0;
    private int wordCount = 0;
    private final HashMap<String, Integer> wordFreq = new HashMap<>();

    private StreamTextProcessor() {
    }

    public static StreamTextProcessor fromFile(Path filePath) {
        StreamTextProcessor processor = new StreamTextProcessor();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(processor::processLine);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file: " + filePath, e);
        }
        return processor;
    }

    private void processLine(String line) {
        lineCount++;
        String[] words = line.split("\\W+");
        wordCount += words.length;
        for (String word : words) {
            if (!word.isBlank()) {
                String cleaned = word.toLowerCase();
                wordFreq.put(cleaned, wordFreq.getOrDefault(cleaned, 0) + 1);
            }
        }
    }

    @Override
    public int getLineCount() {
        return lineCount;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public List<String> getMostFrequentWords(int k) {
        return wordFreq.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
