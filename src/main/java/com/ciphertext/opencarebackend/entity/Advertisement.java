package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.AgeGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "advertisement")
public class Advertisement extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "target_url")
    private String targetUrl;

    @Column(name = "target_type", nullable = false)
    private String targetType;

    @Column(name = "target_id")
    private Long targetId;

    @ManyToOne
    @JoinColumn(name = "advertisement_type_id")
    private AdvertisementType advertisementType;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne
    @JoinColumn(name = "union_id")
    private Union union;

    @ManyToOne
    @JoinColumn(name = "medical_speciality_id")
    private MedicalSpeciality medicalSpeciality;

    @Column(name = "age_group", length = 50)
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @Column(name = "gender", length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "views")
    private Integer views = 0;

    @Column(name = "clicks")
    private Integer clicks = 0;
}