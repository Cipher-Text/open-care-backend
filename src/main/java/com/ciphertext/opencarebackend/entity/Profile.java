package com.ciphertext.opencarebackend.entity;

import com.ciphertext.opencarebackend.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.james.mime4j.dom.datetime.DateTime;

import java.util.Date;

//databaseChangeLog:
//        - changeSet:
//id: 4fcebad2-3cf1-4ba1-83c5-6d7fcc9b4979
//author: sadman
//changes:
//        - createTable:
//columns:
//        - column:
//constraints:
//nullable: false
//primaryKey: true
//primaryKeyName: profile_id_pk
//name: id
//type: bigserial
//              - column:
//constraints:
//nullable: false
//name: username
//type: varchar(100)
//              - column:
//constraints:
//nullable: false
//name: keycloak_user_id
//type: varchar(100)
//              - column:
//name: phone
//type: varchar(20)
//              - column:
//name: email
//type: varchar(100)
//              - column:
//name: name
//type: varchar(100)
//              - column:
//name: bn_name
//type: varchar(100)
//              - column:
//name: gender
//type: varchar(20)
//              - column:
//name: date_of_birth
//type: date
//              - column:
//name: address
//type: varchar(512)
//              - column:
//name: district_id
//type: int4
//              - column:
//name: upazila_id
//type: int4
//              - column:
//name: union_id
//type: int4
//              - column:
//constraints:
//nullable: false
//name: created_by
//type: varchar(100)
//              - column:
//constraints:
//nullable: false
//name: created_at
//type: timestamp
//              - column:
//constraints:
//nullable: false
//name: updated_by
//type: varchar(100)
//              - column:
//constraints:
//nullable: false
//name: updated_at
//type: timestamp
//tableName: profile
//        - addForeignKeyConstraint:
//baseColumnNames: district_id
//baseTableName: profile
//constraintName: profile_fk_district
//deferrable: false
//initiallyDeferred: false
//onUpdate: CASCADE
//referencedColumnNames: id
//referencedTableName: district
//validate: true
//        - addForeignKeyConstraint:
//baseColumnNames: upazila_id
//baseTableName: profile
//constraintName: profile_fk_upazila
//deferrable: false
//initiallyDeferred: false
//onUpdate: CASCADE
//referencedColumnNames: id
//referencedTableName: upazila
//validate: true
//        - addForeignKeyConstraint:
//baseColumnNames: union_id
//baseTableName: profile
//constraintName: profile_fk_union
//deferrable: false
//initiallyDeferred: false
//onUpdate: CASCADE
//referencedColumnNames: id
//referencedTableName: union
//validate: true

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "keycloak_user_id", nullable = false)
    private String keycloakUserId;

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
