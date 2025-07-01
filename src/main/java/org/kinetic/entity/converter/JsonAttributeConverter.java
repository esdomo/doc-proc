package org.kinetic.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public abstract class JsonAttributeConverter<T> implements AttributeConverter<T, String> {

    private final ObjectMapper objectMapper;
    private final TypeReference<T> typeRef;

    protected JsonAttributeConverter(ObjectMapper objectMapper, TypeReference<T> typeRef) {
        this.objectMapper = objectMapper;
        this.typeRef = typeRef;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize attribute: " + attribute, e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, typeRef);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to deserialize attribute: " + dbData, e);
        }
    }
}
