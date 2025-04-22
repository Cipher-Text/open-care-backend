package com.ciphertext.opencarebackend.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalTestRequest {
    private Integer parentId;
    private String name;
    private String bnName;
    private String alternativeNames;
    private String description;
}
