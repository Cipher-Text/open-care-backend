package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.BloodComponent;
import com.ciphertext.opencarebackend.enums.BloodGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.RequisitionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="blood_requisition")
public class BloodRequisition extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private Profile requester;

    @Column(name = "patient_name", length = 200, nullable = false)
    private String patientName;

    @Column(name = "patient_age")
    private Integer patientAge;

    @Column(name = "patient_gender")
    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    @Column(name = "blood_group", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column(name = "blood_component", length = 20)
    @Enumerated(EnumType.STRING)
    private BloodComponent bloodComponent;

    @Column(name = "quantity_bags", nullable = false)
    private Integer quantityBags;

    @Column(name = "needed_by_date", nullable = false)
    private LocalDate neededByDate;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(name = "contact_person", length = 200)
    private String contactPerson;

    @Column(name = "contact_phone", length = 20, nullable = false)
    private String contactPhone;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @Column(name = "lat", precision = 10, scale = 8)
    private BigDecimal lat;

    @Column(name = "lon", precision = 11, scale = 8)
    private BigDecimal lon;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequisitionStatus requisitionStatus;

    @Column(name = "fulfilled_date")
    private LocalDate fulfilledDate;

}
