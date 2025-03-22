package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstitutionResponse {
    private int id;
    private String acronym;
    private String name;
    private String bnName;
    private Integer establishedYear;
    private Integer enroll;
    private DistrictResponse district;
    private HospitalType hospitalType;
    private OrganizationType organizationType;
    private String lat;
    private String lon;
    private String websiteUrl;
}
