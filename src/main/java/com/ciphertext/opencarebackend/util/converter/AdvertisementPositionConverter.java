package com.ciphertext.opencarebackend.util.converter;

import com.ciphertext.opencarebackend.enums.AdvertisementPosition;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AdvertisementPositionConverter implements AttributeConverter<AdvertisementPosition, String> {

    @Override
    public String convertToDatabaseColumn(AdvertisementPosition attribute) {
        return attribute != null ? attribute.getPosition() : null;
    }

    @Override
    public AdvertisementPosition convertToEntityAttribute(String dbData) {
        return dbData != null ? AdvertisementPosition.fromValue(dbData) : null;
    }
}
