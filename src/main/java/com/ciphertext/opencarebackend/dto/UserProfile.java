package com.ciphertext.opencarebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
