package com.ciphertext.opencarebackend.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class KeycloakRegistrationRequest {
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
