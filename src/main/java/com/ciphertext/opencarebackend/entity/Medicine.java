package com.ciphertext.opencarebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medicine", indexes = {
        @Index(name = "idx_medicine_brand_name", columnList = "brand_name"),
        @Index(name = "idx_medicine_generic", columnList = "generic"),
        @Index(name = "idx_medicine_manufacturer", columnList = "manufacturer"),
        @Index(name = "idx_medicine_slug", columnList = "slug"),
        @Index(name = "idx_medicine_type", columnList = "type"),
        @Index(name = "idx_medicine_dosage_form", columnList = "dosage_form"),

        // For case-insensitive search
        @Index(name = "idx_medicine_brand_name_lower", columnList = "lower(brand_name)"),
        @Index(name = "idx_medicine_generic_lower", columnList = "lower(generic)"),

        // For full-text search (will be created via native SQL)
        @Index(name = "idx_medicine_search_vector", columnList = "search_vector")
})
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "dosage_form", nullable = false)
    private String dosageForm;

    @Column(name = "generic", nullable = false)
    private String generic;

    @Column(name = "strength", nullable = false)
    private String strength;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "package_container", nullable = false)
    private String packageContainer;

    @Column(name = "package_size", nullable = false)
    private String packageSize;

    @Column(name = "search_vector", columnDefinition = "tsvector")
    private String searchVector;
}