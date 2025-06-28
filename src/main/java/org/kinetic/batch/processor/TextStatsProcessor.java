package org.kinetic.batch.processor;

import org.kinetic.data.TextStats;
import org.kinetic.service.factory.TextProcessorFactory;
import org.kinetic.service.processor.TextFileProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class TextStatsProcessor implements ItemProcessor<Path, TextStats> {

    private final TextProcessorFactory textProcessorFactory;

    public TextStatsProcessor(TextProcessorFactory textProcessorFactory) {
        this.textProcessorFactory = textProcessorFactory;
    }

    @Override
    public TextStats process(@NonNull Path path) throws IOException {
        TextFileProcessor textProcessor = textProcessorFactory.streamProcessor();
        textProcessor.processFile(path);
        return textProcessor.stats();
    }
}
