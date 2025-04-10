package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;

public interface ProfileService {
    Profile getProfileByKeycloakUserId(String keycloakUserId) throws ResourceNotFoundException;
    Profile updateProfile(String keycloakUserId, Profile profile);
    Profile createProfile(Profile profile);
}
