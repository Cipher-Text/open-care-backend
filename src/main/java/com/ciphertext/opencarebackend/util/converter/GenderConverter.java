package com.ciphertext.opencarebackend.util.converter;

import com.ciphertext.opencarebackend.enums.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return attribute != null ? attribute.getBanglaName() : null;
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) return null;

        return Arrays.stream(Gender.values())
                .filter(g -> g.getBanglaName().equalsIgnoreCase(dbData.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown gender value: " + dbData));
    }
}

