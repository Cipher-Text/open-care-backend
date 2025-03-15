package com.ciphertext.opencarebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdate {
    private String firstName;
    private String lastName;
    private String email;
}
