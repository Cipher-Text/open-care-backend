package com.ciphertext.opencarebackend.entity;


import com.ciphertext.opencarebackend.enums.AdvertisementPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="advertisement_type")
public class AdvertisementType extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name="description", nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private AdvertisementPosition position;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "duration_in_days", nullable = false)
    private Integer durationInDays;
}
