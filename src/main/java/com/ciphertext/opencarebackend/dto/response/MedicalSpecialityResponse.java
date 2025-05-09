package com.ciphertext.opencarebackend.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalSpecialityResponse {
    private Integer id;
    private Integer parentId;
    private String name;
    private String bnName;
    private String icon;
    private String imageUrl;
    private String description;
    private Long doctorCount;
}
