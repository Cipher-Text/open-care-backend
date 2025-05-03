package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.entity.DoctorDegree;
import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class DoctorResponse {
    private Long id;
    private String bmdcNo;
    private LocalDate startDate;
    private Integer yearOfExperience;
    private String degrees;
    private String specializations;
    private String description;
    private Boolean isActive;
    private Boolean isDeleted;
    private ProfileResponse profile;
    private List<DoctorDegreeResponse> doctorDegrees;
    private List<DoctorWorkplaceResponse> doctorWorkplaces;
}
