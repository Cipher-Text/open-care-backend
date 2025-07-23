package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Permission {
    VIEW_ADMIN_PANEL("view_admin_panel", "View Admin Panel", "অ্যাডমিন প্যানেল দেখুন", "This permission allows the user to view the admin panel."),
    
    CREATE_DOCTOR("create-doctor", "Create Doctor", "ডাক্তার তৈরি করুন", "This permission allows the user to create a new doctor profile."),
    UPDATE_DOCTOR("update-doctor", "Update Doctor","ডাক্তার আপডেট করুন", "This permission allows the user to update an existing doctor profile."),
    DELETE_DOCTOR("delete-doctor", "Delete Doctor", "ডাক্তার মুছুন", "This permission allows the user to delete a doctor profile."),

    CREATE_PROFILE("create-profile", "Create Profile", "প্রোফাইল তৈরি করুন", "This permission allows the user to create a new profile."),
    UPDATE_PROFILE("update-profile", "Update Profile", "প্রোফাইল আপডেট করুন", "This permission allows the user to update an existing profile."),
    DELETE_PROFILE("delete-profile", "Delete Profile", "প্রোফাইল মুছুন", "This permission allows the user to delete a profile."),

    CREATE_HOSPITAL("create-hospital", "Create Hospital", "হাসপাতাল তৈরি করুন", "This permission allows the user to create a new hospital profile."),
    UPDATE_HOSPITAL("update-hospital", "Update Hospital", "হাসপাতাল আপডেট করুন", "This permission allows the user to update an existing hospital profile."),
    DELETE_HOSPITAL("delete-hospital", "Delete Hospital", "হাসপাতাল মুছুন", "This permission allows the user to delete a hospital profile."),

    CREATE_SOCIAL_ORGANIZATION("create-social-organization", "Create Social Organization", "সামাজিক সংগঠন তৈরি করুন", "This permission allows the user to create a new social organization profile."),
    UPDATE_SOCIAL_ORGANIZATION("update-social-organization", "Update Social Organization", "সামাজিক সংগঠন আপডেট করুন", "This permission allows the user to update an existing social organization profile."),
    DELETE_SOCIAL_ORGANIZATION("delete-social-organization", "Delete Social Organization", "সামাজিক সংগঠন মুছুন", "This permission allows the user to delete a social organization profile."),

    CREATE_INSTITUTION("create-institution", "Create Institution", "প্রতিষ্ঠান তৈরি করুন", "This permission allows the user to create a new institution profile."),
    UPDATE_INSTITUTION("update-institution", "Update Institution", "প্রতিষ্ঠান আপডেট করুন", "This permission allows the user to update an existing institution profile."),
    DELETE_INSTITUTION("delete-institution", "Delete Institution", "প্রতিষ্ঠান মুছুন", "This permission allows the user to delete an institution profile."),

    CREATE_MASTER_DATA("create-master-data", "Create Master Data", "মাস্টার ডেটা তৈরি করুন", "This permission allows the user to create master data entries."),
    UPDATE_MASTER_DATA("update-master-data", "Update Master Data", "মাস্টার ডেটা আপডেট করুন", "This permission allows the user to update existing master data entries."),
    DELETE_MASTER_DATA("delete-master-data", "Delete Master Data", "মাস্টার ডেটা মুছুন", "This permission allows the user to delete master data entries."),

    CREATE_HOSPITAL_MEDICAL_TEST("create-hospital-medical-test", "Create Hospital Medical Test", "হাসপাতাল মেডিকেল টেস্ট তৈরি করুন", "This permission allows the user to create a new hospital medical test."),
    UPDATE_HOSPITAL_MEDICAL_TEST("update-hospital-medical-test", "Update Hospital Medical Test", "হাসপাতাল মেডিকেল টেস্ট আপডেট করুন", "This permission allows the user to update an existing hospital medical test."),
    DELETE_HOSPITAL_MEDICAL_TEST("delete-hospital-medical-test", "Delete Hospital Medical Test", "হাসপাতাল মেডিকেল টেস্ট মুছুন", "This permission allows the user to delete a hospital medical test.");

    private final String value;
    private final String name;
    private final String banglaName;
    private final String description;

    Permission(String value, String name, String banglaName, String description) {
        this.value = value;
        this.name = name;
        this.banglaName = banglaName;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public String getDescription() {
        return description;
    }

    public static Permission fromString(String value) {
        for (Permission permission : Permission.values()) {
            if (permission.value.equalsIgnoreCase(value)) {
                return permission;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found");
    }
}
