package com.ciphertext.opencarebackend.dto.request;

import com.ciphertext.opencarebackend.entity.District;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.entity.Upazila;
import com.ciphertext.opencarebackend.enums.BloodComponent;
import com.ciphertext.opencarebackend.enums.BloodGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.RequisitionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequisitionRequest {
    private Long requesterId;
    private String patientName;
    private Integer patientAge;
    private String patientGender;
    private String bloodGroup;
    private String bloodComponent;
    private Integer quantityBags;
    private LocalDate neededByDate;
    private Long hospitalId;
    private String contactPerson;
    private String contactPhone;
    private String description;
    private Long districtId;
    private Long upazilaId;
    private BigDecimal lat;
    private BigDecimal lon;
    private String status;
    private LocalDate fulfilledDate;
}
