package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileResponse {
    private Long id;
    private String username;
    private String userType;
    private String keycloakUserId;
    private byte[] photo;
    private String phone;
    private String email;
    private String name;
    private String bnName;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private DistrictResponse district;
    private UpazilaResponse upazila;
    private UnionResponse union;
}
