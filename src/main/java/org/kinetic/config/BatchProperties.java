package org.kinetic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class BatchProperties {

    @Bean
    public Job processFilesJob(JobRepository jobRepository, Step processFilesStep) {
        return new JobBuilder("processFilesJob", jobRepository)
                .start(processFilesStep)
                .build();
    }

    @Bean
    public Step processFilesStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Path> fileItemReader,
            ItemProcessor<Path, String> fileItemProcessor,
            ItemWriter<String> fileItemWriter
    ) {
        return new StepBuilder("processFilesStep", jobRepository)
                .<Path, String>chunk(1, transactionManager)
                .reader(fileItemReader)
                .processor(fileItemProcessor)
                .writer(fileItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Path> fileItemReader(@Value("#{jobParameters['filePaths']}") String filePaths) {
        List<Path> paths = Arrays.stream(filePaths.split(","))
                .map(String::trim)
                .map(Paths::get)
                .collect(Collectors.toList());

        return new IteratorItemReader<>(paths);
    }


    @Bean
    public ItemProcessor<Path, String> fileItemProcessor() {
        return path -> {
            String content = Files.readString(path);
            return "File: " + path.getFileName() + ", chars: " + content.length();
        };
    }

    @Bean
    public ItemWriter<String> fileItemWriter() {
        return items -> items.forEach(log::info);
    }


}