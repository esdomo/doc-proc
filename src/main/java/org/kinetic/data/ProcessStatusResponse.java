package org.kinetic.data;

import java.time.Instant;
import java.util.List;

public record ProcessStatusResponse(
        Long process_id,
        String status,
        Instant started_at,
        Instant completed_at,
        Results results
) {
    public record Results(
            int total_words,
            int total_lines,
            List<String> most_frequent_words,
            List<String> files_processed
    ) {
        public static Results from(TextStats stats, int topK) {
            return new Results(
                    stats.getWordCount(),
                    stats.getLineCount(),
                    stats.getMostFrequentWords(topK),
                    stats.getFileNames()
            );
        }

        public static Results empty() {
            return new Results(
                    0,
                    0,
                    List.of(),
                    List.of()
            );
        }
    }
}
