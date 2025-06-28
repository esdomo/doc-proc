package org.kinetic.service.processor;

import org.kinetic.data.TextStats;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class StreamTextFileProcessor implements TextFileProcessor {

    private final List<String> loadedFiles = new ArrayList<>();
    private int lineCount = 0;
    private int wordCount = 0;
    private final HashMap<String, Integer> wordFreq = new HashMap<>();

    public void processFile(Path filePath) {
        loadedFiles.add(filePath.getFileName().toString());
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(this::processLine);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file: " + filePath, e);
        }
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
    public TextStats stats() {
        return TextStats.builder()
                .fileNames(loadedFiles)
                .wordFreq(wordFreq)
                .lineCount(lineCount)
                .wordCount(wordCount)
                .build();
    }
}
