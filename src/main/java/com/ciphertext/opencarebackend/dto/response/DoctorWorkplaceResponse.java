package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.entity.Doctor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorWorkplaceResponse {
    private Long id;
    private Doctor doctor;
    private String doctorPosition;
    private String teacherPosition;
    private MedicalSpecialityResponse medicalSpeciality;
    private InstitutionResponse institution;
    private HospitalResponse hospital;
    private LocalDate startDate;
    private LocalDate endDate;
}
