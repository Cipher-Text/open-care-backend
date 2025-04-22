package com.ciphertext.opencarebackend.dto.filter;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

@Builder
@Getter
public class MedicalTestFilter {
    private String name;
    private Integer hospitalId;
    private Integer medicalTestId;
    private Integer parentMedicalTestId;
    private Integer minPrice;
    private Integer maxPrice;
}
