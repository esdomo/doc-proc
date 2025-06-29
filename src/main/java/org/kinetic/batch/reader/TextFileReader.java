package org.kinetic.batch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@StepScope
public class TextFileReader implements ItemReader<Path> {

    private final IteratorItemReader<Path> delegate;

    public TextFileReader(@Value("#{stepExecutionContext['filePath']}") String filePath) {
        this.delegate = new IteratorItemReader<>(List.of(Paths.get(filePath)));
    }

    @Override
    public Path read() {
        return delegate.read();
    }
}
