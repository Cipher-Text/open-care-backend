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
@Table(name="hospital", indexes = {
    @Index(name = "idx_hospital_name", columnList = "name"),
    @Index(name = "idx_hospital_district", columnList = "district_id"),
    @Index(name = "idx_hospital_upazila", columnList = "upazila_id"),
    @Index(name = "idx_hospital_union", columnList = "union_id"),
    @Index(name = "idx_hospital_type", columnList = "hospital_type, organization_type"),
    @Index(name = "idx_hospital_location", columnList = "lat, lon")
})
public class Hospital extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="bn_name")
    private String bnName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="number_of_bed", nullable = false)
    private Integer numberOfBed;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "union_id")
    private Union union;

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
