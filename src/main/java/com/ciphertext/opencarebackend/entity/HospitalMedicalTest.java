package com.ciphertext.opencarebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Sadman
 */
@Getter
@Setter
@Entity
@Table(name="hospital_medical_test", indexes = {
        @Index(name = "idx_hospital_medical_test_hospital", columnList = "hospital_id"),
        @Index(name = "idx_hospital_medical_test_test", columnList = "medical_test_id")
})
public class HospitalMedicalTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "medical_test_id")
    private MedicalTest medicalTest;

    @Column(name = "price")
    private Integer price;

    @Column(name = "is_active")
    private Boolean isActive;
}