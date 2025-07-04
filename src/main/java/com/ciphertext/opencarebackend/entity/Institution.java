package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sadman
 */
@Getter
@Setter
@Entity
@Table(name="institution")
public class Institution extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="acronym")
    private String acronym;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="bn_name", nullable = false)
    private String bnName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="established_year")
    private Integer establishedYear;

    @Column(name="enroll")
    private Integer enroll;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "district_id")
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(name = "hospital_type", nullable = false)
    private HospitalType hospitalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type", nullable = false)
    private OrganizationType organizationType;

    @Column(name="lat", nullable = false)
    private String lat;

    @Column(name="lon", nullable = false)
    private String lon;

    @Column(name="website_url", nullable = false)
    private String websiteUrl;
}
