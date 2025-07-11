package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AmbulanceType {
    BASIC("Basic Life Support (BLS)"),
    ADVANCED("Advanced Life Support (ALS)"),
    NEONATAL("Neonatal Ambulance"),
    PEDIATRIC("Pediatric Ambulance"),
    AIR_AMBULANCE("Air Ambulance");

    private final String description;

    AmbulanceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
