package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgeGroup {
    AGE_0_11("0-11", 0, 11),
    AGE_12_17("12+", 12, 17),
    AGE_18_24("18+", 18, 24),
    AGE_25_34("25+", 25, 34),
    AGE_35_44("35+", 35, 44),
    AGE_45_54("45+", 45, 54),
    AGE_55_64("55+", 55, 64),
    AGE_65_PLUS("65+", 65, 200);

    private final String label;
    private final int startAge;
    private final int endAge;

    AgeGroup(String label, int startAge, int endAge) {
        this.label = label;
        this.startAge = startAge;
        this.endAge = endAge;
    }


    public String getLabel() {
        return label;
    }

    public int getStartAge() {
        return startAge;
    }

    public int getEndAge() {
        return endAge;
    }
}
