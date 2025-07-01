package org.kinetic.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Converter
public class FileNamesConverter extends JsonAttributeConverter<List<String>> {

    public FileNamesConverter(ObjectMapper objectMapper) {
        super(objectMapper, new TypeReference<>() {});
    }
}