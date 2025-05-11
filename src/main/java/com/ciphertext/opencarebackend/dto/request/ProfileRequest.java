package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileRequest {
    private String username;
    private String userType;
    private byte[] photo;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String name;
    private String bnName;
    private String gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private String address;
    private Integer districtId;
    private Integer upazilaId;
    private Integer unionId;
}
