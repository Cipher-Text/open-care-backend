package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdvertisementRequest {

    private String title;
    private String content;
    private String imageUrl;
    private String targetUrl;
    private String targetType;
    private Long targetId;
    private Integer advTypeId;
    private Long districtId;
    private Long upazilaId;
    private Long unionId;
    private Long medSpecialityId;
    private String ageGroup;
    private String gender;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private Long views;
    private Long clicks;
    private String createdBy;
}
