package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDegreeRequest {
    private Long doctorId;
    private Long degreeId;
    private Long specialityId;
    private Long institutionId;
    private String startDateTime;
    private String endDateTime;
    private String grade;
    private String description;
}
