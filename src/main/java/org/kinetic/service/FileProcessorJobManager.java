package org.kinetic.service;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.ProcessStatusResponse;
import org.kinetic.data.TextStats;
import org.kinetic.exception.ProcessNotFoundException;
import org.kinetic.repository.TextStatsRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
@Slf4j
public class FileProcessorJobManager {

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final Job processFilesJob;
    private final TextStatsRepository textStatsRepository;

    public FileProcessorJobManager(JobLauncher asyncJobLauncher, JobExplorer jobExplorer, Job processFilesJob, TextStatsRepository textStatsRepository) {
        this.jobLauncher = asyncJobLauncher;
        this.jobExplorer = jobExplorer;
        this.processFilesJob = processFilesJob;
        this.textStatsRepository = textStatsRepository;
    }

    public Long startBatchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        long timestamp = System.currentTimeMillis();

        JobParameters jobParams = new JobParametersBuilder()
                .addLong("timestamp", timestamp)
                .toJobParameters();

        JobExecution run = jobLauncher.run(processFilesJob, jobParams);
        log.info("Launched job with ID: {} at {}", run.getJobId(), timestamp);
        return run.getJobId();
    }

    public ProcessStatusResponse getResult(Long jobId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(jobId);
        if (jobExecution == null) {
            throw new ProcessNotFoundException("Job with id " + jobId + " not found");
        } else if (jobExecution.isRunning()) {
            return new ProcessStatusResponse(
                    jobId,
                    jobExecution.getStatus().name(),
                    jobExecution.getStartTime().toInstant(ZoneOffset.UTC),
                    null,
                    ProcessStatusResponse.Results.empty()
            );

        } else {
            TextStats textStats = TextStats.fromEntity(textStatsRepository.getReferenceById(jobId));
            return new ProcessStatusResponse(
                    jobId,
                    jobExecution.getStatus().name(),
                    jobExecution.getStartTime().toInstant(ZoneOffset.UTC),
                    jobExecution.getEndTime().toInstant(ZoneOffset.UTC),
                    ProcessStatusResponse.Results.from(textStats, 5)
            );
        }
    }
}
