package org.kinetic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileProcessorJobManager {

    private final JobLauncher jobLauncher;
    private final Job processFilesJob;

    public FileProcessorJobManager(JobLauncher asyncJobLauncher, Job processFilesJob) {
        this.jobLauncher = asyncJobLauncher;
        this.processFilesJob = processFilesJob;
    }

    public Long startBatchJob() throws Exception {

        long timestamp = System.currentTimeMillis();
        JobParameters jobParams = new JobParametersBuilder()
                .addLong("timestamp", timestamp)
                .toJobParameters();

        JobExecution run = jobLauncher.run(processFilesJob, jobParams);
        log.info("Launched job with ID: {} at {}", run.getJobId(), timestamp);
        return run.getJobId();
    }

}
