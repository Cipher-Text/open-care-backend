package com.ciphertext.opencarebackend.dto.filter;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class HospitalFilter {
    private String name;
    private String bnName;
    private Integer numberOfBed;
    private List<Integer> districtIds;
    private Integer upazilaId;
    private Integer unionId;
    private List<String> hospitalTypes;
    private String organizationType;
}
