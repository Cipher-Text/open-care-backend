package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.entity.District;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpazilaResponse {
    private int id;
    private District district;
    private String name;
    private String bnName;
    private String url;
}
