package com.ciphertext.opencarebackend.enums;


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

    public String getPosition() {
        return position;
    }

}