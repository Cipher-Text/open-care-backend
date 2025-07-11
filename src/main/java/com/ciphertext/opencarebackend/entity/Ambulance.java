package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.AgeGroup;
import com.ciphertext.opencarebackend.enums.AmbulanceType;
import com.ciphertext.opencarebackend.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//              - column:
//name: vehicle_number
//type: VARCHAR(20)
//constraints:
//nullable: false
//unique: true
//        - column:
//name: type
//type: VARCHAR(50)
//              - column:
//name: driver_name
//type: VARCHAR(100)
//              - column:
//name: driver_phone
//type: VARCHAR(20)
//              - column:
//name: is_available
//type: BOOLEAN
//defaultValue: true
//        - column:
//name: hospital_id
//type: INTEGER
//constraints:
//references: hospital(id)
//foreignKeyName: fk_ambulance_hospital
//deleteCascade: true

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ambulance")
public class Ambulance extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vehicle_number", nullable = false, unique = true, length = 20)
    private String vehicleNumber;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AmbulanceType ambulanceType;

    @Column(name = "driver_name", nullable = false, length = 100)
    private String driverName;

    @Column(name = "driver_phone", nullable = false, length = 20)
    private String driverPhone;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}