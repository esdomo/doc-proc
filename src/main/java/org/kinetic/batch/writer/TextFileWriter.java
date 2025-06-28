package org.kinetic.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.data.TextStats;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TextFileWriter implements ItemWriter<TextStats> {

    @Override
    public void write(Chunk<? extends TextStats> textFileMetaData) {
        textFileMetaData.forEach(textStats -> log.info(textStats.toString()));
    }
}
