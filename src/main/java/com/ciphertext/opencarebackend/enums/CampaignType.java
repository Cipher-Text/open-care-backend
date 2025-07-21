package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CampaignType {
    BLOOD_DONATION("Blood Donation", "রক্তদান"),
    HEALTH_CHECKUP("Health Checkup", "স্বাস্থ্য পরীক্ষা"),
    AWARENESS_PROGRAM("Awareness Program", "সচেতনতা প্রোগ্রাম"),
    VACCINATION("Vaccination", "টিকাদান"),
    COMMUNITY_SERVICE("Community Service", "কমিউনিটি সার্ভিস");

    private final String shortName;
    private final String banglaName;

    CampaignType(String shortName, String banglaName) {
        this.shortName = shortName;
        this.banglaName = banglaName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getBanglaName() {
        return banglaName;
    }
}
