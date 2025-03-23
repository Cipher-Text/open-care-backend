package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalSpecialityResponse {
    private Integer id;
    private MedicalSpecialityResponse parentMedicalSpeciality;
    private String name;
    private String bnName;
    private String description;
}
