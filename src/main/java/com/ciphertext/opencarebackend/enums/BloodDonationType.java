package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BloodDonationType {
    WHOLE_BLOOD("Whole Blood"),
    PLASMA("Plasma"),
    PLATELETS("Platelets"),
    DOUBLE_RED("Double Red");

    private final String name;

    BloodDonationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
