package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorWorkplaceRequest {
    private Long doctorId;
    private Long medicalSpecialityId;
    private Long institutionId;
    private Long hospitalId;
    private String doctorPosition;
    private String teacherPosition;
    private String startDateTime;
    private String endDateTime;
    private String description;
}
