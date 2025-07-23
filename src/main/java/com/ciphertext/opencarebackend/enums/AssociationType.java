package com.ciphertext.opencarebackend.enums;

public enum AssociationType {
    GENERAL("General"),
    SPECIAL("Special"),
    INFORMAL("Informal");

    private final String name;

    AssociationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
