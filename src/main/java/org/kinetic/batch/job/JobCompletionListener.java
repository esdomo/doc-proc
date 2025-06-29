package org.kinetic.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.TextStats;
import org.kinetic.service.TextStatsAggregator;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.ObjectFactory;

@Slf4j
public class JobCompletionListener implements JobExecutionListener {
    private final ObjectFactory<TextStatsAggregator> aggregatorFactory;

    public JobCompletionListener(ObjectFactory<TextStatsAggregator> aggregatorFactory) {
        this.aggregatorFactory = aggregatorFactory;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        TextStats aggregated = aggregatorFactory.getObject().aggregate();
        log.info("Job started at {}" , jobExecution.getStartTime());
        log.info("Job completed at {}" , jobExecution.getEndTime());
        log.info("Aggregated TextStats: {}", aggregated.toString());
    }
}
