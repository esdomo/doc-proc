package org.kinetic.batch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@StepScope
public class TextFileReader implements ItemReader<Path> {

    private final IteratorItemReader<Path> delegate;

    public TextFileReader(@Value("#{jobParameters['filePaths']}") String filePaths) {
        List<Path> paths = Arrays.stream(filePaths.split(","))
                .map(String::trim)
                .map(Paths::get)
                .collect(Collectors.toList());
        this.delegate = new IteratorItemReader<>(paths);
    }

    @Override
    public Path read() {
        return delegate.read();
    }
}
