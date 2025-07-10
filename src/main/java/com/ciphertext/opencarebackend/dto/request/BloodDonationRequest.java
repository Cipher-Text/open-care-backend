package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BloodDonationRequest {
    private Long donorId;
    private LocalDate donationDate;
    private String bloodType;
    private Long quantityMl;
    private String donationType;
    private String healthStatus;
    private Double hemoglobinLevel;
    private String bloodPressure;
    private Long pulseRate;
    private Double temperature;
    private String notes;
    private String status;
}
