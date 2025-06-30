package org.kinetic.data;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
public class TextStats {

    private List<String> fileNames;
    private int wordCount;
    private int lineCount;
    private HashMap<String, Integer> wordsFreq;

    public List<String> getMostFrequentWords(int k) {
        return wordsFreq.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    //TODO: Remove, this is just for testing
    @Override
    public String toString() {
        return "{\n" +
                "  \"total_words\": " + wordCount + ",\n" +
                "  \"total_lines\": " + lineCount + ",\n" +
                "  \"most_frequent_words\": " + getMostFrequentWords(5) + ",\n" +
                "  \"files_processed\": " + fileNames + "\n" +
                "}";
    }
}
