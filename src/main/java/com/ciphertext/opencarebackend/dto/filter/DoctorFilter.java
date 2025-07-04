package com.ciphertext.opencarebackend.dto.filter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DoctorFilter {
    private String name;
    private String bnName;
    private String bmdcNo;
    private Boolean isCurrentWorkplace;
    private Integer districtId;
    private Integer upazilaId;
    private Integer unionId;
    private Integer specialityId;
    private Integer degreeId;
    private Integer hospitalId;
    private Integer workInstitutionId;
    private Integer studyInstitutionId;
}
