package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineResponse {
    private Integer id;
    private Integer brandId;
    private String brandName;
    private String type;
    private String slug;
    private String dosageForm;
    private String generic;
    private String strength;
    private String manufacturer;
    private String packageContainer;
    private String packageSize;
}
