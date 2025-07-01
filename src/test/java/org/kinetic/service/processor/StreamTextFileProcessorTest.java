package org.kinetic.service.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.kinetic.data.TextStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamTextFileProcessorTest {

    @TempDir
    Path tempDir;

    @Test
    void processesFileAndCollectsStats() throws IOException {
        Path file = tempDir.resolve("sample.txt");
        Files.writeString(file, "Hello, world? world!\nAnother line.");

        StreamTextFileProcessor processor = new StreamTextFileProcessor();
        processor.processFile(file);
        TextStats stats = processor.stats();

        assertEquals(List.of("sample.txt"), stats.getFileNames());
        assertEquals(2, stats.getLineCount());
        assertEquals(5, stats.getWordCount());
        Map<String, Integer> freq = stats.getWordsFreq();
        assertEquals(4, freq.size());
        assertEquals(1, freq.get("hello"));
        assertEquals(2, freq.get("world"));
        assertEquals(1, freq.get("another"));
        assertEquals(1, freq.get("line"));
    }
}
