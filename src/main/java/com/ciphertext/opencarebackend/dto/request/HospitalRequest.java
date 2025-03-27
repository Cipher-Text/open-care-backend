package com.ciphertext.opencarebackend.dto.request;

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
