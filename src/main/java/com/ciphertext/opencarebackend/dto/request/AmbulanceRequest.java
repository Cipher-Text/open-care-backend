package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmbulanceRequest {
    private String vehicleNumber;
    private String type;
    private String driverName;
    private String driverPhone;
    private Integer hospitalId;
    private Integer upazilaId;
    private Integer districtId;
}
