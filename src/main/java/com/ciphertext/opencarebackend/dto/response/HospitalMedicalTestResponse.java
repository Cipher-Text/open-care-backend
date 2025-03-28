package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalMedicalTestResponse {
    private Long id;
    private HospitalResponse hospital;
    private MedicalTestResponse medicalTest;
    private Integer price;
    private Boolean isActive;
}
