package org.kinetic.controller;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.service.FileProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/process")
@Slf4j
public class ProcessController {

    private final FileProcessorService fileProcessorService;

    public ProcessController(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }

    @PostMapping("start")
    public ResponseEntity<String> processFiles() {
        log.info("Received request to start document processing.");
        try {
            Long jobId = fileProcessorService.startBatchJob();
            return ResponseEntity.ok("Started process with id: " + jobId);
        } catch (Exception e) {
            log.error("Job failed", e);
            return ResponseEntity.internalServerError().body("Failed to process files");
        }
    }
}
