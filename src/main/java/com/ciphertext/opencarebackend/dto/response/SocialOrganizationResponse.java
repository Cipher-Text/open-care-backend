package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.Country;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SocialOrganizationResponse {
    private Integer id;
    private String name;
    private String bnName;
    private String organizationType;
    private LocalDateTime foundedDate;
    private String description;
    private String address;
    private String websiteUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String youtubeUrl;
    private String email;
    private String phone;
    private String originCountry;
}
