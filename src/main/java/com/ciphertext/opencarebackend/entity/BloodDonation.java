package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.BloodComponent;
import com.ciphertext.opencarebackend.enums.BloodGroup;
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

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Profile donor;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Column(name = "blood_group", nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column(name = "quantity_ml")
    private Long quantityMl = 450L;

    @Column(name = "blood_component", length = 20)
    @Enumerated(EnumType.STRING)
    private BloodComponent bloodComponent;
}
