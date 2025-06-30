package org.kinetic.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.TextStats;
import org.kinetic.entity.TextStatsEntity;
import org.kinetic.repository.TextStatsRepository;
import org.kinetic.service.TextStatsAggregator;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.ObjectFactory;

@Slf4j
public class JobCompletionListener implements JobExecutionListener {
    private final ObjectFactory<TextStatsAggregator> aggregatorFactory;
    private final TextStatsRepository repository;

    public JobCompletionListener(ObjectFactory<TextStatsAggregator> aggregatorFactory,
                                 TextStatsRepository repository) {
        this.aggregatorFactory = aggregatorFactory;
        this.repository = repository;
    }

    @Override
    public synchronized void afterJob(JobExecution jobExecution) {
        TextStats aggregated = aggregatorFactory.getObject().aggregate();
        log.info("Job started at {}", jobExecution.getStartTime());
        log.info("Job completed at {}", jobExecution.getEndTime());
        log.info("Aggregated TextStats: {}", aggregated.toString());
        repository.save(TextStatsEntity.builder()
                .jobId(jobExecution.getJobId())
                .wordCount(aggregated.getWordCount())
                .lineCount(aggregated.getLineCount())
                .wordsFreq(aggregated.getWordsFreq())
                .fileNames(aggregated.getFileNames())
                .build());
    }
}
