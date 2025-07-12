package com.ciphertext.opencarebackend.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementTypeRequest {

    private String name;
    private String description;
    private String position;
    private Double basePrice;
    private Integer durationInDays;
}
