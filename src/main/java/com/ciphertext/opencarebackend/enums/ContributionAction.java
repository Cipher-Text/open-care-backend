package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContributionAction {

    PROFILE_COMPLETION("profile_completion", 10, "Completed user profile"),
    FIRST_LOGIN("first_login", 2, "Logged in for the first time"),
    ADD_DOCTOR("add_doctor", 30, "Added a new doctor"),
    ADD_HOSPITAL("add_hospital", 30, "Added a new hospital or institution"),
    SUBMIT_REVIEW("submit_review", 5, "Submitted a review for a doctor or hospital"),
    SUGGEST_CORRECTION("suggest_correction", 10, "Suggested a correction to existing data"),
    UPLOAD_PROFILE_PHOTO("upload_profile_photo", 5, "Uploaded profile picture"),
    REFER_FRIEND("refer_friend", 20, "Referred a friend who signed up"),
    VERIFY_DOCTOR("verify_doctor", 15, "Provided certificate/ID for doctor verification"),
    DATA_VERIFIED_BY_ADMIN("data_verified_by_admin", 5, "User-submitted data verified by admin");

    private final String code;
    private final int points;
    private final String description;

    ContributionAction(String code, int points, String description) {
        this.code = code;
        this.points = points;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    // Optional: Lookup by code
    public static ContributionAction fromCode(String code) {
        for (ContributionAction rule : ContributionAction.values()) {
            if (rule.code.equalsIgnoreCase(code)) {
                return rule;
            }
        }
        throw new IllegalArgumentException("Unknown point rule code: " + code);
    }
}
