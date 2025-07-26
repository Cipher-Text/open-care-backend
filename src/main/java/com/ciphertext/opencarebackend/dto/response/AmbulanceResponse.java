package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AmbulanceResponse {
    private Long id;
    private String vehicleNumber;
    private String type;
    private String driverName;
    private String driverPhone;
    private Boolean isAvailable;
    private HospitalResponse hospital;
    private UpazilaResponse upazila;
    private DistrictResponse district;
    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
