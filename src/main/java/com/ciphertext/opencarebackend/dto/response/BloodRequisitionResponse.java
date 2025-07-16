package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.BloodGroup;
import com.ciphertext.opencarebackend.enums.Gender;
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
public class BloodRequisitionResponse {
    private Long id;
    private Long requesterId;
    private String patientName;
    private Integer patientAge;
    private Gender patientGender;
    private BloodGroup bloodGroup;
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
