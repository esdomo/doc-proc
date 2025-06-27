package org.kinetic.service.factory;

import org.kinetic.service.processor.StreamTextProcessor;
import org.kinetic.service.processor.TextProcessor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class TextProcessorFactory {
    public TextProcessor streamProcessor(Path filePath) {
        return StreamTextProcessor.fromFile(filePath);
    }
}