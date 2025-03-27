package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Sadman
 */
@Getter
@Setter
@Entity
@Table(name="doctor")
public class Doctor extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="bmdc_no")
    private String bmdcNo;

    @Column(name="degrees")
    private String degrees;

    @Column(name="specializations")
    private String specializations;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="description")
    private String description;

    @Column(name="is_verified")
    private Boolean isVerified;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
