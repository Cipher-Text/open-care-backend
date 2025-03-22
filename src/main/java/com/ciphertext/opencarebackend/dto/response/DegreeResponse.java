package com.ciphertext.opencarebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DegreeResponse {
    private Integer id;
    private String name;
    private String abbreviation;
    private String degreeType;
}
