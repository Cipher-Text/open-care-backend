package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DaysOfWeek {
    SUNDAY("রবিবার", "sun"),
    MONDAY("সোমবার", "mon"),
    TUESDAY("মঙ্গলবার", "tue"),
    WEDNESDAY("বুধবার", "wed"),
    THURSDAY("বৃহস্পতিবার", "thu"),
    FRIDAY("শুক্রবার", "fri"),
    SATURDAY("শনিবার", "sat");

    private final String banglaName;
    private final String abbreviatedName;

    DaysOfWeek(String banglaName, String abbreviatedName) {
        this.banglaName = banglaName;
        this.abbreviatedName = abbreviatedName;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }
}
