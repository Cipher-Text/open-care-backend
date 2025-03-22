package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalResponse {
    private Integer id;
    private String name;
    private String bnName;
    private Integer numberOfBed;
    private DistrictResponse district;
    private UpazilaResponse upazila;
    private UnionResponse union;
    private HospitalType hospitalType;
    private OrganizationType organizationType;
    private String lat;
    private String lon;
    private String websiteUrl;
}
