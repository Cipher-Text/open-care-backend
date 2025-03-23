package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.entity.Institution;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DoctorDegreeResponse {
    private Long id;
    private DoctorResponse doctor;
    private DegreeResponse degree;
    private MedicalSpecialityResponse medicalSpeciality;
    private Institution institution;
    private Date startDateTime;
    private Date endDateTime;
    private String grade;
    private String description;
}
