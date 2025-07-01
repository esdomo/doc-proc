package org.kinetic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.ProcessStatusResponse;
import org.kinetic.service.FileProcessorJobManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/process")
@Slf4j
@Tag(name = "Document Processing", description = "Endpoints for starting jobs and checking their status")
public class ProcessController {

    private final FileProcessorJobManager fileProcessorJobManager;

    public ProcessController(FileProcessorJobManager fileProcessorJobManager) {
        this.fileProcessorJobManager = fileProcessorJobManager;
    }

    @Operation(
            summary = "Start file processing job",
            description = "Launches a new asynchronous job that processes all text files in the configured folder.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The started job id", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PostMapping("start")
    public ResponseEntity<String> processFiles() throws Exception {
        log.info("Received request to start document processing.");
        Long jobId = fileProcessorJobManager.startBatchJob();
        log.info("Started process with id: {}", jobId);
        return ResponseEntity.ok(String.valueOf(jobId));
    }

    @Operation(
            summary = "Get job results",
            description = "Retrieves the processing status and statistics for a previously started job.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved job results"),
                    @ApiResponse(responseCode = "404", description = "Job not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("results/{jobId}")
    public ResponseEntity<ProcessStatusResponse> getProcessResult(
            @Parameter(description = "Identifier of the job to query")
            @PathVariable Long jobId
    ) {
        log.info("Received request to get process results.");
        ProcessStatusResponse result = fileProcessorJobManager.getResult(jobId);
        return ResponseEntity.ok(result);
    }
}
