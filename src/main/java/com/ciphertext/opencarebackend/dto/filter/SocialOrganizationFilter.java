package com.ciphertext.opencarebackend.dto.filter;

import com.ciphertext.opencarebackend.enums.Country;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
