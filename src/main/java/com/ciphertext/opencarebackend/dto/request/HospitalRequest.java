package com.ciphertext.opencarebackend.dto.request;

import com.ciphertext.opencarebackend.dto.response.DistrictResponse;
import com.ciphertext.opencarebackend.dto.response.UnionResponse;
import com.ciphertext.opencarebackend.dto.response.UpazilaResponse;
import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalRequest {
    private Integer id;
    private String name;
    private String bnName;
    private Integer numberOfBed;
    private Integer districtId;
    private Integer upazilaId;
    private Integer unionId;
    private String hospitalType;
    private String organizationType;
    private String lat;
    private String lon;
    private String websiteUrl;
}
