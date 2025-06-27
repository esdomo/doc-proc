package org.kinetic.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TextFileWriter implements ItemWriter<String> {

    @Override
    public void write(Chunk<? extends String> textFileMetaData) {
        textFileMetaData.forEach(log::info);
    }
}
