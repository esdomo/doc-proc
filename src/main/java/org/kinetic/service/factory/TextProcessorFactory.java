package org.kinetic.service.factory;

import org.kinetic.service.processor.StreamTextFileProcessor;
import org.kinetic.service.processor.TextFileProcessor;
import org.springframework.stereotype.Component;

@Component
public class TextProcessorFactory {
    public TextFileProcessor streamProcessor() {
        return new StreamTextFileProcessor();
    }
}