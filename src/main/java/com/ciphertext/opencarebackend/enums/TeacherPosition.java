package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TeacherPosition {
    PROFESSOR("অধ্যাপক"),
    ASSOCIATE_PROFESSOR("সহযোগী অধ্যাপক"),
    ASSISTANT_PROFESSOR("সহকারী অধ্যাপক"),
    LECTURER("প্রভাষক");

    private final String banglaName;

    TeacherPosition(String banglaName) {
        this.banglaName = banglaName;
    }

    public String getBanglaName() {
        return banglaName;
    }
}
