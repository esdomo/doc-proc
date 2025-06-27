package org.kinetic.service.processor;

import java.util.List;

public interface TextProcessor {

    int getLineCount();

    int getWordCount();

    List<String> getMostFrequentWords(int top);
}
