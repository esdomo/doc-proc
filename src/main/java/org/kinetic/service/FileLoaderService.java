package org.kinetic.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.kinetic.config.AppConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileLoaderService {

    private final Path inputFolder;

    public FileLoaderService(AppConfig appConfig) {
        this.inputFolder = Paths.get(appConfig.getInputFolder());
    }

    public List<Path> listTextFiles() throws IOException {
        if (!Files.exists(inputFolder)) {
            throw new IllegalArgumentException("Input folder not found: " + inputFolder);
        }

        try (var files = Files.list(inputFolder)) {
            return files
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".txt"))
                    .map(Path::toAbsolutePath)
                    .collect(Collectors.toList());
        }
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(inputFolder);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create input folder: " + inputFolder, e);
        }
    }
}
