package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalMedicalTestRequest {
    private Long hospitalId;
    private Long medicalTestId;
    private Integer price;
    private Boolean isActive;
}
