package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.Country;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import com.ciphertext.opencarebackend.enums.SocialOrganizationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Sadman
 */
@Getter
@Setter
@Entity
@Table(name="social_organization")
public class SocialOrganization extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="bn_name")
    private String bnName;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type", nullable = false)
    private SocialOrganizationType socialOrganizationType;

    @Column(name="founded_date")
    private LocalDateTime foundedDate;

    @Column(name="description")
    private String description;

    @Column(name="address")
    private String address;

    @Column(name="website_url")
    private String websiteUrl;

    @Column(name="facebook_url")
    private String facebookUrl;

    @Column(name="twitter_url")
    private String twitterUrl;

    @Column(name="linkedin_url")
    private String linkedinUrl;

    @Column(name="youtube_url")
    private String youtubeUrl;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="origin_country")
    @Enumerated(EnumType.STRING)
    private Country originCountry;
}
