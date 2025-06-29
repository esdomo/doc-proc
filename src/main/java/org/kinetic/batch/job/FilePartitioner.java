package org.kinetic.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.exception.FilePartitionException;
import org.kinetic.service.FileLoaderService;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FilePartitioner implements Partitioner {

    private final FileLoaderService fileLoaderService;

    public FilePartitioner(FileLoaderService fileLoaderService) {
        this.fileLoaderService = fileLoaderService;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> partitions = new HashMap<>();
        try {

            List<Path> files = fileLoaderService.listTextFiles();
            for (int i = 0; i < files.size(); i++) {
                ExecutionContext context = new ExecutionContext();
                context.putString("filePath", files.get(i).toString());
                partitions.put("partition_" + i, context);
            }
        } catch (IOException e) {
            log.error("Failed to read files from input folder", e);
            throw new FilePartitionException("Partitioning failed due to file read error", e);
        }
        return partitions;
    }
}
