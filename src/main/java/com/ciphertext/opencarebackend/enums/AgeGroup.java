package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgeGroup {
    AGE_18_24("18-24", 18, 24),
    AGE_25_34("25-34", 25, 34),
    AGE_35_44("35-44", 35, 44),
    AGE_45_54("45-54", 45, 54),
    AGE_55_64("55-64", 55, 64),
    AGE_65_PLUS("65+", 65, 200); // 200 as a practical upper limit

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

    // Optional: helper method to match age to group
    public static AgeGroup fromAge(int age) {
        for (AgeGroup group : values()) {
            if (age >= group.startAge && age <= group.endAge) {
                return group;
            }
        }
        throw new IllegalArgumentException("No matching age group for age: " + age);
    }
}

