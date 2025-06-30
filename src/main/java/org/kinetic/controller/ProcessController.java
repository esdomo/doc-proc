package org.kinetic.controller;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.ProcessStatusResponse;
import org.kinetic.service.FileProcessorJobManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/process")
@Slf4j
public class ProcessController {

    private final FileProcessorJobManager fileProcessorJobManager;

    public ProcessController(FileProcessorJobManager fileProcessorJobManager) {
        this.fileProcessorJobManager = fileProcessorJobManager;
    }

    @PostMapping("start")
    public ResponseEntity<String> processFiles() throws Exception {
        log.info("Received request to start document processing.");
        Long jobId = fileProcessorJobManager.startBatchJob();
        log.info("Started process with id: {}", jobId);
        return ResponseEntity.ok(String.valueOf(jobId));
    }

    @GetMapping("results/{jobId}")
    public ResponseEntity<ProcessStatusResponse> getProcessResult(@PathVariable Long jobId) {
        log.info("Received request to get process results.");
        ProcessStatusResponse result = fileProcessorJobManager.getResult(jobId);
        return ResponseEntity.ok(result);
    }
}
