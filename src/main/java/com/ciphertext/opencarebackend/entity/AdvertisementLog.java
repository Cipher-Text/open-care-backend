package com.ciphertext.opencarebackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "advertisement_log")
public class AdvertisementLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @Column(name = "action", nullable = false, length = 20)
    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile user;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @Column(name = "device_type", length = 50)
    private String deviceType;

    @Column(name = "os", length = 50)
    private String os;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}