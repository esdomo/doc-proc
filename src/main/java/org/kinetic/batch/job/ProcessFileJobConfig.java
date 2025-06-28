package org.kinetic.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.TextStats;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Path;

@Configuration
@Slf4j
public class ProcessFileJobConfig {

    @Bean
    public Job processFilesJob(JobRepository jobRepository, Step masterStep) {
        return new JobBuilder("processFilesJob", jobRepository)
                .start(masterStep)
                .build();
    }

    @Bean
    public Step processFilesStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Path> textFileReader,
            ItemProcessor<Path, TextStats> textFileProcessor,
            ItemWriter<TextStats> textFileWriter
    ) {
        return new StepBuilder("processFilesStep", jobRepository)
                .<Path, TextStats>chunk(1, transactionManager)
                .reader(textFileReader)
                .processor(textFileProcessor)
                .writer(textFileWriter)
                .build();
    }

    //Async setup

    @Bean
    public JobLauncher asyncJobLauncher(JobRepository jobRepository, SimpleAsyncTaskExecutor batchTaskExecutor) {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(batchTaskExecutor);
        return jobLauncher;
    }

    /**
     * MasterStep creates partitions for each fileName provided by the filePartitioner
     * Each partition is assigned to a worker which executes the processFileStep
     */
    @Bean
    public Step masterStep(JobRepository jobRepository,
                           Step processFilesStep,
                           FilePartitioner filePartitioner,
                           SimpleAsyncTaskExecutor batchTaskExecutor) {
        return new StepBuilder("masterStep", jobRepository)
                .partitioner("processFilesStep", filePartitioner)
                .step(processFilesStep)
                .taskExecutor(batchTaskExecutor)
                .build();
    }

    @Bean
    public SimpleAsyncTaskExecutor batchTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("file-proc-task");
        executor.setConcurrencyLimit(8);
        return executor;
    }
}