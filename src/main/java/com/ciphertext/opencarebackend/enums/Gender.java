package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
    MALE("পুরুষ"),
    FEMALE("মহিলা"),
    OTHERS("অন্যান্য");

    private final String banglaName;

    Gender(String banglaName) {
        this.banglaName = banglaName;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public static Gender fromString(String value) {
        if (value == null) return null;
        try {
            return Gender.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Or throw custom exception if preferred
        }
    }

    public static String fromEnum(Gender gender) {
        return gender != null ? gender.name() : null;
    }
}
