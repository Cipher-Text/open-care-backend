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

    @Column(name = "advertisement_type_id")
    private Integer advTypeId;

    @ManyToOne
    @JoinColumn(name = "advertisement_type_id", insertable=false, updatable=false)
    private AdvertisementType advertisementType;

    @Column(name = "district_id")
    private Long districtId;

    @ManyToOne
    @JoinColumn(name = "district_id", insertable=false, updatable=false)
    private District district;

    @Column(name = "upazila_id")
    private Long upazilaId;

    @ManyToOne
    @JoinColumn(name = "upazila_id", insertable=false, updatable=false)
    private Upazila upazila;

    @Column(name = "union_id")
    private Long unionId;

    @ManyToOne
    @JoinColumn(name = "union_id", insertable=false, updatable=false)
    private Union union;

    @Column(name = "medical_speciality_id")
    private Long medSpecialityId;

    @ManyToOne
    @JoinColumn(name = "medical_speciality_id", insertable=false, updatable=false)
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