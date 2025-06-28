package org.kinetic.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileProcessorJobManager {

    private final FileLoaderService fileLoaderService;
    private final JobLauncher jobLauncher;
    private final Job processFilesJob;

    public FileProcessorJobManager(FileLoaderService fileLoaderService, JobLauncher jobLauncher, Job processFilesJob) {
        this.fileLoaderService = fileLoaderService;
        this.jobLauncher = jobLauncher;
        this.processFilesJob = processFilesJob;
    }

    public Long startBatchJob() throws Exception {
        List<Path> paths = fileLoaderService.listTextFiles();
        String fileList = paths.stream()
                .map(Path::toString)
                .collect(Collectors.joining(","));

        JobParameters jobParams = new JobParametersBuilder()
                .addString("filePaths", fileList)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        JobExecution run = jobLauncher.run(processFilesJob, jobParams);
        return run.getJobId();
    }

}
