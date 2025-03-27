package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorResponse {
    private Long id;
    private String bmdcNo;
    private LocalDate startDate;
    private String degrees;
    private String specializations;
    private String description;
    private Boolean isActive;
    private Boolean isDeleted;
    private ProfileResponse profile;
}
