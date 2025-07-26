package com.ciphertext.opencarebackend.entity;

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
    private String type;

    @Column(name = "driver_name", length = 100)
    private String driverName;

    @Column(name = "driver_phone", length = 20)
    private String driverPhone;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @ManyToOne
    @JoinColumn(name = "hospital_id", insertable = false, updatable = false)
    private Hospital hospital;

    @Column(name = "upazila_id")
    private Long upazilaId;

    @ManyToOne
    @JoinColumn(name = "upazila_id", insertable = false, updatable = false)
    private Upazila upazila;

    @Column(name = "district_id")
    private Long districtId;

    @ManyToOne
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy = "admin";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy = "admin";

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}