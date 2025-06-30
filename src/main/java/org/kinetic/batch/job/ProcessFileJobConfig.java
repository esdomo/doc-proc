package org.kinetic.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.TextStats;
import org.kinetic.repository.TextStatsRepository;
import org.kinetic.service.TextStatsAggregator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.JobSynchronizationManager;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Path;

@Configuration
@Slf4j
public class ProcessFileJobConfig {

    @Bean
    public Job processFilesJob(JobRepository jobRepository,
                               Step masterStep,
                               JobCompletionListener jobCompletionListener) {
        return new JobBuilder("processFilesJob", jobRepository)
                .start(masterStep)
                .listener(jobCompletionListener)
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

    @Bean
    public JobCompletionListener jobCompletionListener(ObjectFactory<TextStatsAggregator> aggregatorFactory,
                                                       TextStatsRepository repository) {
        return new JobCompletionListener(aggregatorFactory, repository);
    }

    @Bean
    @JobScope
    public TextStatsAggregator textStatsAggregator() {
        return new TextStatsAggregator();
    }

    //Async setup

    @Bean
    public JobLauncher asyncJobLauncher(JobRepository jobRepository) {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor("job-"));
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
                           TaskExecutor workerTaskExecutor) {
        return new StepBuilder("masterStep", jobRepository)
                .partitioner("processFilesStep", filePartitioner)
                .step(processFilesStep)
                .taskExecutor(workerTaskExecutor)
                .build();
    }


    // Register calling thread JobExecution on new threads so it can keep the context.
    @Bean
    public TaskExecutor workerTaskExecutor(){
        return new SimpleAsyncTaskExecutor() {
            @Override
            protected void doExecute(Runnable task) {
                JobExecution jobExecution = JobSynchronizationManager.getContext().getJobExecution();
                String workerPrefix = String.format("job-%d-worker-", jobExecution.getJobId());
                setThreadNamePrefix(workerPrefix);
                super.doExecute(() -> {
                    JobSynchronizationManager.register(jobExecution);
                    try {
                        task.run();
                    } finally {
                        JobSynchronizationManager.release();
                    }
                });
            }
        };
    }
}