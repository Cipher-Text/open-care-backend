package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.entity.Upazila;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnionResponse {
    private int id;
    private Upazila upazila;
    private String name;
    private String bnName;
    private String url;
}
