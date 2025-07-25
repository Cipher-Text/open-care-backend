package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmbulanceRequest {


//    private Long id;
    private String vehicleNumber;
    private String type;
    private String driverName;
    private String driverPhone;
//    private Boolean isAvailable;
    private Long hospitalId;
    private Long upazilaId;
    private Long districtId;
//    private Boolean isActive;
//    private String createdBy;
//    private LocalDateTime createdAt;
//    private String updatedBy;
//    private LocalDateTime updatedAt;
}
