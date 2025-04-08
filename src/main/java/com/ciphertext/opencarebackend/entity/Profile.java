package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "keycloak_user_id", nullable = false)
    private String keycloakUserId;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "bn_name")
    private String bnName;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "union_id")
    private Union union;
}
