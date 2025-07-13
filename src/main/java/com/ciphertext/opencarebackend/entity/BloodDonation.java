package com.ciphertext.opencarebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="blood_donations")
public class BloodDonation extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "donor_id", nullable = false)
    private Long donorId;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Column(name = "blood_group", nullable = false, length = 5)
    private String bloodType;

    @Column(name = "quantity_ml")
    private Long quantityMl = 450L;

    @Column(name = "donation_type", length = 20)
    private String donationType;

    @Column(name = "health_status", length = 20)
    private String healthStatus;

    @Column(name = "hemoglobin_level", precision = 3, scale = 1)
    private BigDecimal hemoglobinLevel;

    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    @Column(name = "pulse_rate")
    private Long pulseRate;

    @Column(name = "temperature", precision = 3, scale = 1)
    private BigDecimal temperature;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "status", length = 20)
    private String status;
}
