package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.AmbulanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ambulance")
public class Ambulance extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number", nullable = false, length = 20, unique = true)
    private String vehicleNumber;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private AmbulanceType type;

    @Column(name = "driver_name", length = 100)
    private String driverName;

    @Column(name = "driver_phone", length = 20)
    private String driverPhone;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}