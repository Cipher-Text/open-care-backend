package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DegreeType {
    UNDERGRADUATE("অস্নাতক"),
    GRADUATE("স্নাতক"),
    POSTGRADUATE("স্নাতকোত্তর");

    private final String banglaName;

    DegreeType(String banglaName) {
        this.banglaName = banglaName;
    }

    public String getBanglaName() {
        return banglaName;
    }
}
