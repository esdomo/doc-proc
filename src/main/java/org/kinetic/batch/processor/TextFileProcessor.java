package org.kinetic.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TextFileProcessor implements ItemProcessor<Path, String> {

    @Override
    public String process(Path path) throws IOException {
        String content = Files.readString(path);
        return "File: " + path.getFileName() + ", chars: " + content.length();
    }
}
