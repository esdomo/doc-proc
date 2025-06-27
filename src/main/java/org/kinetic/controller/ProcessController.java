package org.kinetic.controller;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.service.FileLoaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/process")
@Slf4j
public class ProcessController {

    private final FileLoaderService fileLoaderService;

    public ProcessController(FileLoaderService fileLoaderService) {
        this.fileLoaderService = fileLoaderService;
    }

    @PostMapping("start")
    public ResponseEntity<String> processFiles() {
        log.info("Received request to start document processing.");
        try {
            List<Path> paths = fileLoaderService.listTextFiles();
            String fileNames = paths.stream().map(Path::toString).collect(Collectors.joining(", "));
            return ResponseEntity.ok("Processing files: " + fileNames);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not load files");
        }
    }
}
