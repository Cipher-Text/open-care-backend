package com.ciphertext.opencarebackend.dto.filter;

import com.ciphertext.opencarebackend.enums.Country;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SocialOrganizationFilter {
    private String name;
    private String bnName;
    private String socialOrganizationType;
    private String email;
    private String phone;
    private Country originCountry;
}
