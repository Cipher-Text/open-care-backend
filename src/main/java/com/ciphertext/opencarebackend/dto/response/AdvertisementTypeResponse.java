package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.AdvertisementPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementTypeResponse {

    private Integer id;
    private String name;
    private String description;
    private AdvertisementPosition position;
    private Double basePrice;
    private Integer durationInDays;
}
