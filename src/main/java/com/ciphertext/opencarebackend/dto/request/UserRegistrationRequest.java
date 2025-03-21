package com.ciphertext.opencarebackend.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private List<Credential> credentials;

    @Data
    public static class Credential {
        private String type;
        private String value;
        private boolean temporary;
    }
}
