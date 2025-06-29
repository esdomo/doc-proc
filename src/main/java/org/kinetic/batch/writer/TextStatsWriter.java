package org.kinetic.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.TextStats;
import org.kinetic.service.TextStatsAggregator;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TextStatsWriter implements ItemWriter<TextStats> {

    private final TextStatsAggregator aggregator;

    public TextStatsWriter(TextStatsAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public void write(Chunk<? extends TextStats> textFileMetaData) {
        textFileMetaData.forEach(aggregator::addStats);
    }
}
