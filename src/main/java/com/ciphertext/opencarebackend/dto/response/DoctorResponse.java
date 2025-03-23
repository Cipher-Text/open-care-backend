package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorResponse {
    private Long id;
    private String name;
    private String bnName;
    private Gender gender;
    private String bmdcNo;
    private String phone;
    private String email;
    private String address;
    private LocalDate startDate;
    private String description;
    private byte[] image;
    private Boolean isActive;
}
