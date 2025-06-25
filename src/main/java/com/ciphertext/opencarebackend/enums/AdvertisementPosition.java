package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdvertisementPosition {
    HEADER("Header"),
    FOOTER("Footer"),
    SIDEBAR("Sidebar"),
    IN_CONTENT("In Content");

    private final String position;

    AdvertisementPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
