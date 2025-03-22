package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictResponse {
    private Integer id;
    private DivisionResponse division;
    private String name;
    private String bnName;
    private String lat;
    private String lon;
    private String url;
}
