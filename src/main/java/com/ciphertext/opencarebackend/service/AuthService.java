package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.request.RegistrationRequest;

public interface AuthService {
    /**
     * Registers a new user with the provided registration request.
     *
     * @param registrationRequest the registration request containing user details
     * @return a message indicating the result of the registration
     */
    String registerUser(RegistrationRequest registrationRequest);
}
