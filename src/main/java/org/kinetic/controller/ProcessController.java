package org.kinetic.controller;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.service.FileProcessorJobManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/process")
@Slf4j
public class ProcessController {

    private final FileProcessorJobManager fileProcessorJobManager;

    public ProcessController(FileProcessorJobManager fileProcessorJobManager) {
        this.fileProcessorJobManager = fileProcessorJobManager;
    }

    @PostMapping("start")
    public ResponseEntity<String> processFiles() {
        log.info("Received request to start document processing.");
        try {
            Long jobId = fileProcessorJobManager.startBatchJob();
            return ResponseEntity.ok("Started process with id: " + jobId);
        } catch (Exception e) {
            log.error("Job failed", e);
            return ResponseEntity.internalServerError().body("Failed to process files");
        }
    }
}
