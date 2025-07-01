package org.kinetic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.kinetic.config.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileLoaderServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldOnlyListTextFiles() throws IOException {
        Files.writeString(tempDir.resolve("a.txt"), "alpha");
        Files.writeString(tempDir.resolve("b.md"), "beta");
        Files.writeString(tempDir.resolve("c.txt"), "gamma");

        AppConfig config = new AppConfig();
        config.setInputFolder(tempDir.toString());
        FileLoaderService service = new FileLoaderService(config);
        service.init();

        List<Path> actualFiles = service.listTextFiles();
        List<Path> expected = List.of(tempDir.resolve("a.txt").toAbsolutePath(),
                tempDir.resolve("c.txt").toAbsolutePath());

        assertEquals(2, actualFiles.size());
        assertEquals(expected, actualFiles);
    }
}