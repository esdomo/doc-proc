package org.kinetic.batch.processor;

import org.kinetic.service.factory.TextProcessorFactory;
import org.kinetic.service.processor.TextProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public class TextFileProcessor implements ItemProcessor<Path, String> {

    private final TextProcessorFactory textProcessorFactory;

    public TextFileProcessor(TextProcessorFactory textProcessorFactory) {
        this.textProcessorFactory = textProcessorFactory;
    }

    @Override
    public String process(Path path) throws IOException {
        TextProcessor textProcessor = textProcessorFactory.streamProcessor(path);
        int lineCount = textProcessor.getLineCount();
        int wordCount = textProcessor.getWordCount();
        List<String> mostFrequentWords = textProcessor.getMostFrequentWords(5);
        return "File: " + path.getFileName() + "\n" +
                "Line count: " + lineCount + "\n" +
                "Word count: " + wordCount + "\n" +
                "Top 10 most frequent words: " + mostFrequentWords + "\n";
    }
}
