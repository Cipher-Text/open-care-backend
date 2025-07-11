package com.ciphertext.opencarebackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private String bloodGroup;
    private String gender;
    private Integer districtId;
}
