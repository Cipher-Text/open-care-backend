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

}
