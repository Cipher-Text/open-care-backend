package com.ciphertext.opencarebackend.enums;

public enum RequisitionStatus {
    ACTIVE("Active"),
    FULFILLED("Fulfilled"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled");

    private final String name;

    RequisitionStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
