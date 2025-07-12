package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertisementPosition {
    HEADER("Header"),
    FOOTER("Footer"),
    SIDEBAR("Sidebar"),
    IN_CONTENT("In Content"),
    SEARCH_TOP("search_top"),
    PAGE_TOP("page_top"),
    DIRECTORY_SIDEBAR("directory_sidebar"),
    HOME_MIDDLE("home_middle"),
    VIDEO_SLOT("video_slot"),
    BADGE("badge"),
    EQUIPMENT_SECTION("equipment_section"),
    EVENT_BANNER("event_banner"),
    ANNOUNCEMENT_BAR("announcement_bar"),
    CAROUSEL("carousel");

    private final String position;

    AdvertisementPosition(String position) {
        this.position = position;
    }

    @JsonValue
    public String getPosition() {
        return position;
    }

    @JsonCreator
    public static AdvertisementPosition fromValue(String value) {
        for (AdvertisementPosition ap : AdvertisementPosition.values()) {
            if (ap.getPosition().equalsIgnoreCase(value)) {
                return ap;
            }
        }
        throw new IllegalArgumentException("Unknown AdvertisementPosition: " + value);
    }
}