package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
    SUPER_ADMIN("সুপার অ্যাডমিন"),
    ADMIN("অ্যাডমিন"),
    MODERATOR("মডারেটর"),
    OPERATOR("অপারেটর"),
    HOSPITAL_ADMIN("হাসপাতাল অ্যাডমিন"),
    INSTITUTION_ADMIN("প্রতিষ্ঠান অ্যাডমিন"),
    SOCIAL_ORGANIZATION_ADMIN("সামাজিক সংগঠন অ্যাডমিন"),
    DOCTOR("ডাক্তার"),
    USER("ব্যবহারকারী");

    private final String banglaName;

    UserType(String banglaName) {
        this.banglaName = banglaName;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public UserType fromString(String value) {
        try {
            return UserType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided string doesn't match any enum constant
            return null; // or throw an exception or return a default value
        }
    }

    public String fromEnum(UserType gender){
        return gender.name();
    }
}
