package com.ciphertext.opencarebackend.util.converter;

import com.ciphertext.opencarebackend.enums.AgeGroup;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AgeGroupConverter implements AttributeConverter<AgeGroup, String> {
    @Override
    public String convertToDatabaseColumn(AgeGroup attribute) {
        return attribute != null ? attribute.getLabel() : null;
    }

    @Override
    public AgeGroup convertToEntityAttribute(String dbData) {
        return dbData != null ? AgeGroup.fromLabel(dbData) : null;
    }
}
