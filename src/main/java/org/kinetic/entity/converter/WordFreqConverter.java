package org.kinetic.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Converter
public class WordFreqConverter extends JsonAttributeConverter<Map<String, Integer>> {

    public WordFreqConverter(ObjectMapper objectMapper) {
        super(objectMapper, new TypeReference<>() {
        });
    }
}
