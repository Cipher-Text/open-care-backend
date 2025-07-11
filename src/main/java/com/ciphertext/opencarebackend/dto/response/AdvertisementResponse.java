package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.AgeGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdvertisementResponse {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String targetUrl;
    private String targetType;
    private Long targetId;
    private Integer advTypeId;
    private AdvertisementTypeResponse advertisementType;
    private Long districtId;
    private DistrictResponse district;
    private Long upazilaId;
    private UpazilaResponse upazila;
    private Long unionId;
    private UnionResponse union;
    private Long medSpecialityId;
    private MedicalSpecialityResponse medicalSpeciality;
    private AgeGroup ageGroup;
    private Gender gender;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private Long views;
    private Long clicks;
}
